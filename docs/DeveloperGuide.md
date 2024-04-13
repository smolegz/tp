---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# LookMeUp Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

LookMeUp  is a brownfield software project based off AddressBook Level-3, taken under the CS2103T Software Engineering,
at National University of Singapore.

1. The UI features of `AddCommandHelper` was reused with minimal changes from [Snom](https://github.com/RunjiaChen/ip).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### Safe-Removal feature

#### Implementation

The feature to remove contacts from the address book is facilitated by `RemoveCommand` and `RemoveConfirmation`.

The safe-removal mechanism consists of several components:
1. `RemoveCommand`: The main command class that performs the preparation of removal in 2 main parts: shortlisting the
target person to be removed by matching contact name, and seeking confirmation of the removal process.
2. `RemoveCommandParser`: A class that parses the user input to determine the target person to be removed. The class
   parses the `Predicate` input when users key in `remove NAME`, to aid in the shortlisting process. The class also parses
   the `Index` input when users key in `remove INDEX`, to proceed with the confirmation process of the actual contact to
   be removed.
3. `RemoveConfirmation`, `RemoveSuccess` and `RemoveAbortion`: Classes that prompt the user to confirm the removal of
   the target person, performing the actual deletion of the contact (or abortion of process), then providing feedback on
   the success or failure of the removal process.

Our implementation follows Liskov's Substitution Principle closely. `RemoveConfirmation` was designed to be an abstract
class to allow for extension of the 2 confirmation methods via the `RemoveSuccess` and `RemoveAbortion` classes. This
decision makes it easier to group similar methods and messages together for better code extendability and
maintainability when it comes to enhancing the confirmation process.

Given below is an example usage scenario and how the safe-removal mechanism behaves at each step.
> Assuming existing contacts in the address book (shown in a simplified list for ease of understanding):
> 1. Paul Walker
> 2. Alice Cooper
> 3. Dylan Walker
> 4. Paul Cooper

* **Step 1**: The user executes `remove Paul` command.
    * The `remove` command calls `RemoveCommandParser#parseCommand()`, causing `RemoveCommand#execute()` to get called
  in response.
    * `RemoveCommand` will shortlist the target person to be removed by matching the contact name with the input.
        * The input will be parsed by `RemoveCommandParser` to obtain the intended `Predicate`, in this case, `Paul`.
    > Matching contacts:
  > 1. Paul Walker
  > 2. Paul Cooper

    * `RemoveCommand` will then prompt the user to key in the index of the contact to remove. e.g. `remove 1`
  > **_NOTE:_** This step can be foregone if the user is very sure of the INDEX of the contact to be removed from the
    > original list in the address book. The user can key in `remove INDEX` and proceed with Step 2 directly.


* **Step 2**: The user executes `remove 1` command.
    * The `remove` command calls `RemoveCommandParser#parseCommand()`, causing `RemoveCommand#execute()` to get called
    * `RemoveCommand` will proceed with the confirmation process of the actual contact to be removed.
        * The input will be parsed by `RemoveCommandParser` to obtain the intended `Index` to be removed.
        * User will then be prompted to confirm the removal of the contact with "yes"/"no"
        * The user will then key in `yes` or `no` to confirm or abort the removal process.
  > Are you sure you want to remove the following contact? (yes/no):
    > 1. Paul Walker



* **Step 3a**: The user confirms the removal of the contact by executing `yes` command.
    * The `yes` command calls `RemoveSuccess#execute()` to confirm the removal process.
    * The confirmation process will be handled by `RemoveSuccess` and its parent class `RemoveConfirmation`.
        * `RemoveSuccess#execute()` checks if the `yes` input is valid, calling `RemoveConfirmation#isValidInput()`
        * `RemoveConfirmation#isValidInput()` will return `true` if the input is valid, and `false` otherwise.
            * Validity of input is determined by the previous command executed by the user - a valid `remove INDEX`
          command, that serves as a precursor to the removal  confirmation process.
    * If the user confirms the removal with `yes`, `RemoveSuccess` will proceed with the removal process.
        * The contact will be removed from the address book and `RemoveSuccess` will provide feedback on the success of 
      the removal process.


* **Step 3b**: The user aborts the removal of the contact by executing `no` command.
    * The `no` command calls `RemoveAbortion#execute()` to abort the removal process.
    * The abortion process will be handled by `RemoveAbortion` and its parent class `RemoveConfirmation`.
        * `RemoveAbortion#execute()` checks if the `no` input is valid, calling `RemoveConfirmation#isValidInput()`
        * `RemoveConfirmation#isValidInput()` will return `true` if the input is valid, and `false` otherwise.
            * Validity of input is determined by the previous command executed by the user - a valid `remove INDEX`
          command, that serves as a precursor to the removal abortion process.
  * If the user aborts the removal with `no`, `RemoveCommand` will abort the removal process.
      * The default list of contacts will be shown with the text input of the `CommandBox` cleared, and `RemoveAbortion`
    will provide feedback on the abortion of the removal process.


#### Design considerations:

Several design considerations were taken into account when implementing the safe-removal feature.

_**FIRST CATEGORY**: For shortlisting the contact to be removed and seeking confirmation of the contact to be removed: 


* **Alternative 1 (current choice)**: To use the same command word (i.e. `remove` - `remove NAME` and `remove INDEX`)
to perform the shortlisting of contacts with matching names, as well as the confirmation of the contact to be removed.
  * Pros: Simplifies the command structure, more intuitive for users to approach removal process
    * Using the same command word `remove` for both shortlisting and confirmation processes reduces the cognitive load,
    allowing the process to be more user-friendly
  * Cons: May lead to ambiguity in the command execution process to new developers who are used to the conventions set
  by other commands, as this command structure makes use of an overloaded `RemoveCommand` constructor


* **Alternative 2**: To use the existing `find` command to shortlist the contact to be removed, then use the `delete`
command to perform the deletion
* Pros: Separates the shortlisting and confirmation processes
  * This reduces ambiguity in the command execution process for future developers
* Cons: Require 2 different commands for deletion
  * Increasing the number of commands required to perform the removal process, makes it less intuitive for users to
      approach the removal process. Furthermore, when it comes to **removing contacts**, users might find it a lot more
        intuitive (as it is self-explanatory) to use a`delete`/`remove` command instead of having to use `find` first,
        where in a natural logical context, most users would only use `find` if they are simply looking for a contact.


**Decision**:
Weighing the pros and cons of Alternatives 1 and 2, we have decided to go with **Alternative 1** due to the enhanced
user experience it provides, making the removal process more intuitive and user-friendly.

**Other considerations**:
1. **Single Responsibility Principle**: The `RemoveCommand` class is designed to handle both the shortlisting and
confirmation processes. It is natural for concerns to arise regarding adhering to the Single Responsibility Principle.
However, this class is focused on the single task of preparation for deletion, without handling the actual deletion.
There is still a clear separation of concerns, with `RemoveCommand` class handling the preparation (shortlisting and
prompting of confirmation), and `RemoveConfirmation`, `RemoveSuccess`, `RemoveAbortion` classes handling the actual
deletion. Thus, the Single Responsibility Principle is still adhered to.
2. **Re-branding of `delete` to `remove`**: Given most people will use the original `delete` directly in `delete INDEX`
we have decided to re-brand the double-purpose command as `remove` to avoid confusion. Using a replacement word that 
is explanatory and intuitive in the context of the contact deletion process, while subtle, adds to the overall user 
experience.


_**SECOND CATEGORY**: Mechanism to perform the actual deletion upon confirmation_

Given the key purpose of this feature is for **SAFE** deletion, this step is crucial to ensure that there is a safety
net for users before the actual removal of the contact.


* **Alternative 1 (current choice)**: To prompt the users for confirmation via a `yes`/`no`, then proceed with 
parsing the `yes`/`no` user input as independent commands in `AddressBookParser`
  * Pros: Maintains a similar command structure/workflow as all other commands (to go through `AddressBookParser`)
  * Cons: Requires backward reference of previous command to check if the input was a valid `remove INDEX` input, 
  currently implemented in `RemoveConfirmation#isValidInput()`
    * This leads to a more complex implementation, as details of the previous command might be accidentally exposed if 
    not implemented carefully.


* **Alternative 2**: To create functions that directly handle the confirmation process within `RemoveCommand`
  * Pros: Removes the need to access the previous command and assess its validity 
  * Cons: Changes the command structure/workflow, making it less intuitive for users to approach the removal process
    * This alternative would require a more complex implementation, as the confirmation process would be directly 
    handled within `RemoveCommand`, leading to a more monolithic class structure. This would make it harder to 
    maintain and extend the code in the future, as the class would be responsible for the preparation (shortlisting and 
    confirmation) processes **AND** the actual process of deleting the contact, violating the Single Responsibility 
    Principle.
  

**Decision**: 
Weighing the pros and cons of Alternatives 1 and 2, we have decided to go with **Alternative 1**

Addressing the cons of Alternative 1, our current implementation is such that details of previous command are retrieved 
from `RemoveCommandParser` within the `RemoveConfirmation#isValidInput()` method. This avoids exposure of the 
`remove INDEX` command details, ensuring Separation of Concerns and adhering to the Single Responsibility Principle as 
the `RemoveConfirmation` class solely handles the confirmation process itself and checks directly related to it.


### Fuzzy Input

#### Implementation

The BK-Tree data structure was employed by the implementation of the fuzzy input to effectively find words that are
close to the target word in terms of their Levenshtein distance. Each node in the tree-like data structure represents a
word and its children represent words that are one edit distance away. 

The fuzzy input implementation consists of several components:
<puml src="diagrams/FuzzyInputClassDiagram.puml" alt="FuzzyInputClassDiagram" width="250"/>
1. `BkTreeCommandMatcher`: The main BK-Tree data structure for sorting and efficiently search for similar elements
2. `BkTreeNode`: Internal node structure used by the Bk-Tree
3. `FuzzyCommandParser`: A class demonstrating the usage of BK-tree for command parsing
4. `LevenshteinDistance`: An implementation of the DistanceFunction interface using the Levenshtein distance algorithm
<br/>
<puml src="diagrams/FuzzyInputObjectDiagram.puml" alt="FuzzyInputObjectDiagram" />

Our implementation follows the SOLID principle closely. We have designed interfaces to promote flexibility, especially
complying with the Open-Close Principle. This design decision makes it easy to extend various `CommandMatchers` or
`DistanceFunctions` in the future, making it easier to incorporate alternative algorithms if need be.

Given below is an example usage scenario and how the fuzzy input mechanism behaves:

* Step 1 : User misspelled listing command `lust` instead of `list`. 
  * The `lust` command calls `FuzzyCommandParser#parseCommand())`, causing `BkTreeCommandMatcher#findClosestMatch()` to
  get called in response.
  * The `BkTree` would be already initialised with the list of commands before the call.
    * During the initialisation, `BkTree` calculates the distances between items and constructs the tree accordingly.
  * When `findCLosestMatch()` is called, it initiates a search within the `BkTree` constructed.
    * Starting from root node, Bk-Tree traverses through nodes based on the distance between the target item `lust` 
    and items stored in each `BkTreeNode`.
    * The closest match found based on the specified distance metric (1 misspell) will be returned, in this case `list`
    and `AddressBookParser#parseCommand()` will proceed on to the `list command`.
  * When calculating the distance between 2 items, `BkTree` calls `DistanceFunction#calculateDistance()` method.
    * In this case, LevenshteinDistance class will calculate the distance.

* Step 2 : User entered unsupported command `peek`
    * The `peek` command calls `FuzzyCommandParser#parseCommand())`, causing `BkTreeCommandMatcher#findClosestMatch()` to
      get called in response.
    * Initialisation works the same as Step 1
    * `findClosestMatch()` does the same operation as Step 1
      * However, based on the LevenshteinDistance algorithm, the distance between `peek` and any items stored in
      `BkTreeNode` will be greater than 1 which is greater than the specified distance metric
      * `FuzzyCommandParser#parseCommand())` will return `null` string to `AddressBookParser#parseCommand()`
      * Since `null` is not a recognised command, `ParseException` will be thrown.

    
#### Design considerations:

[Common fuzzy search algorithm for approximate string matching](https://www.baeldung.com/cs/fuzzy-search-algorithm) 
were compared to determine the optimal algorithm for our AddressBook. 

* **Alternative 1 (current choice)** Bk-Tree with Levenshtein Distance Algorithm 
* Pros: Tree-like data structure
  * The hierarchical structure of BK-Tree allows search operations to run in logarithmic time,
  making them scalable for large datasets
  * BK-Tree can work with different types of data, not limited to strings
* Cons: Require more memory, a concern for memory-constrained environment

* **Alternative 2** Hamming Distance
  * Pros: Straightforward to calculate and understand
  * Cons: Only designed for comparing strings of equal length

* **Alternative 3** Bitap Algorithm
  * Pros: Efficient for finding approximate matches of given pattern within a text
  * Cons: Primarily designed for substring matching within texts

* **Alternative 4** Brute Force Method
  * Pros: Easily to implement, no pre-processing required, takes no extra space
  * Cons: Horrible run-time

For our AddressBook implementation, the `BK-Tree with Levenshtein Distance Algorithm` proved to be the optimal choice.
Its memory usage and complexity of implementation outweighs its potential to extend code and efficiently handle
misspelled or similar commands. This algorithm guarantees fast runtime performance and robustness in command parsing.

### Sort feature

#### Implementation

The sorting mechanism is facilitated by `SortCommand`. It implements the following operations:
* `SortCommand#`: Constructor class which is instantiated and stores the necessary `SortStrategy` based on user input.
* `SortCommand#Executes`: Executes the necessary `SortStrategy` and update the model. 

The sorting mechanism consists of several components:
1. `SortStrategy`: An interface that requires implementations to define methods for sorting the address book and getting
the category associated with the sorting strategy.
2. `SortByTag` and `SortByName`: These classes implement `SortStrategy` interface to provide the specific strategies
of the AddressBook based on tags and names respectively. 
3. `SortCommand`: Initiates the sorting by parsing user input to determine the sorting criteria and calls the appropriate
sorting class based on the input. After sorting, it then updates the list of persons in the model. 

Given below is an example usage scenario and how the sorting mechanism behaves at each step.

* Step 1: The user launches the application for the first time, no contacts will be present in the `AddressBook`.
When user `add` contacts in the `AddressBook`, contacts will be sorted based on their timestamp.

* Step 2: The user executes `sort name` command.
  * The `sortCommand#` constructor will initialise with the `sortByName` strategy stored as `SortStrategy`.
  * `sortCommand#execute` will pass the current model's `AddressBook` to `sortStrategy#sort`, where `UniquePersonsList` 
  will be obtained and sorted lexicographically by name 
  * After sorting, the model will be updated to reflect the newly sorted contacts list, alongside a return statement
  to provide confirmation to the user.

    <puml src="diagrams/SortCommandSequenceDiagram.puml" alt="SortCommandSequenceDiagram" />
    
* Step 3: The user executes `sort tag` command.
    * The `sortCommand#` constructor will initialise with the `sortByTag` strategy stored as `SortStrategy`.
    * `sortCommand#execute` will pass the current model's `AddressBook` to `sortStrategy#sort`, where `UniquePersonsList`
      will be obtained and sorted lexicographically by tags
    * After sorting, the model will be updated to reflect the newly sorted contacts list, alongside a return statement
      to provide confirmation to the user.

* Step 4: The user executes `sort` command.
  * The `sortCommand#` constructor will first verify the presence of `condition input` before proceeding with 
  initialisation.
  * Since there is no condition stated, a `ParseException` will be thrown and a statement will be displayed to provide 
  the correct input and conditions to be stated.

    <puml src="diagrams/SortCommandActivityDiagram.puml" alt="SortCommandActivityDiagram" />

#### Design consideration:
`SolidStrategy` interface was implemented for sorting functionality to adhere to SOLID principles, particularly the
Single Responsibility Principle, Interface Segregation Principle and Open/Close Principle.
* Single Responsibility Principle
  * The interface maintains single responsibility by defining methods for sorting strategies without burdening
  implementations with unrelated methods
* Open/Closed Principle
  * The interface provides an abstraction that allows for extension. New sorting strategies can be introduced by
  implementing `SortStrategy` interface without altering existing code.
* Interface Segregation Principle
  * Segregates behavior for sorting into distinct methods `sort` and `getCategory`, thus, allowing different sorting
  strategies to implement only the methods they need, rather than being forced to implement monolithic interface with
  unnecessary methods.
<br/>
* **Alternative 1 (current choice)** `sort` method of the `SortStrategy` to take in `AddressBook` as its parameter.
  * Pros: Straightforward design and easy to implement.
    * Sorting logic interacts directly with data structure being sorted.
  * Cons: May be challenging to apply sorting strategies to different data structures without modification.

* **Alternative 2** `sort` method of the `SortStrategy` to take in `model` as its parameter.
  * Pros: Sorting strategies can be applied to different data structures without modification
    * Promoting code reuse and scalability.
  * Cons: Requires access to `AddressBook` eventually, introducing unnecessary complexity.

Alternative 1 is chosen for the following reasons:
* Simplicity: keeps sorting logic simple and focused by directly interacting with the data structure being sorted.
* Clear Responsibility: Sorting logic is closely tied to the data structure it operates on, adhering to the Single
Responsibility Principle.
* Ease of implementation: No need to pass unnecessary parameters to the sorting method.
  * Reduce complexity and potential dependencies.
  * Clear outline has been established that the only data structure present is the `AddressBook` containing
  `UniquePersonList`.
    * There is not a need to apply sorting strategies to another different data structure.


### Add By Step

#### Overview
`addbystep` loads up a separate window, which will prompt the users for the necessary input fields for an `add` command.
When all the fields have been successfully entered by the user, the user can copy the formatted command to their 
clipboard.

#### Implementation

The architecture diagram given below explains a high-level design of the `addbystep` feature.


The `addbystep` feature is facilitated by the `AddCommandHelper` and the `CommandHelperWindow` class. The 
`CommandHelperWindow` serves as the UI for the user to interact with the `AddCommandHelper`. The `AddCommandHelper` is 
responsible for accepting and checking whether the user's input is valid or not before prompting the user for the next
input field. 



Given below is an example usage scenario and how the `AddCommandHelper` class behaves at each step. Note that while each 
step for accepting fields may come off as repetitive, the type of invalid inputs for each field is different. Thus, we 
wish to illustrate examples of invalid inputs for each field.

* Step 1: The user enters the `addbystep` command, causing the `CommandHelperWindow` to load up. It prompts the user for 
the name of the new contact.

* Step 2: The user enters the name of the new contact.
    * If the name entered by the user is invalid (i.e. not alphanumeric), an error message will be shown and the user
will have to enter the name again 
    * If the name entered by the user is valid, the user will be prompted to enter the next field (number)

* Step 3: The user enters the number of the new contact.
    * If the number entered by the user is invalid (i.e. one digit), an error message will be shown and the user will
will have to enter the number again
    * If the number entered by the user is valid, the user will be prompted to enter the next field (email) 

* Step 4: The user enters the email of the new contact.
    * If the email entered by the user is invalid (i.e. does not have the `@` symbol) an error message will be shown 
and the user will have to enter the email again
    * If the email entered by the user is valid, the user will be prompted to enter the next field (address)

* Step 5: The user enters the address of the new contact.
    * If the address entered by the user is invalid (i.e. blank), an error message will be shown and the user will have 
to enter the address again
    * If the address entered by the user is valid, the user will be prompted to type the copy command (`cp`)

From steps 2 - 5, attached below is an activity diagram of how the user interacts with the `AddCommandHelper` when they 
are keying in the necessary inputs. The `AddCommandHelper` continuously validates the user's input to ensure that they
have entered all the necessary fields correctly.

* Step 6: The user enters the `cp` command.
    * The user can enter anything at this stage, but only the `cp` command will result in the formatted `add` command 
to be copied to the clipboard. Other inputs will result in the same prompt message at the end of Step 5

* Step 7: The successfully copied message will be displayed to the user, and the user can now close the
`CommandHelperWindow` window.
    * The user can still continue interacting with the `CommandHelperWindow`, but those interactions are
meaningless, thus we will not go into the details of those interactions. 


Below is an activity diagram that summarizes the process of a user using the `addbystep` feature. 

<puml src="diagrams/AddByStepActivityDiagram.puml" alt="AddByStepActivityDiagram" />

#### Design considerations:

Aspect: How to implement assistance functions to aid users in typing their commands.

* **Alternative 1 (current choice)** Create a new helper class and GUI to prompt users for the necessary details.
  * Pros:
    * It is easy to implement a new class, and due to the high cohesion of the previous code, we are able to reuse
      methods defined previously in `ParserUtil`class to check the validity of the fields entered by the user
    * The `CommandHelper` class can be implemented separately from the rest of the classes. This results in lower coupling
      between the newly implemented `CommandHelper` class and the remaining classes, resulting in easier maintenance and
      integration
  * Cons:
      * The startup of another GUI for the helper class may introduce lag, especially on the older computers

* **Alternative 2** Implement a command to display the format for users to follow.
  * Pros:
    * It easier to implement as compared to the `CommandHelper` class, since prompts do not actually have any form of user
    interaction
  * Cons:
      * It does not benefit users as much, as they can still make mistakes when it comes to following the exact format
    of the command

* **Alternative 3** Implement a function to autocomplete commands for users.
  * Pros:
      * It can be built directly into the original GUI for AddressBook, there is no need for a separate GUI for the
      `CommandHelper` class
  * Cons:
      * Autocomplete is only able to fill in certain parts of the command for the user (i.e. the prefixes for names, 
      tags). It cannot fill in the exact details 
      * It is more difficult to implement as the users may try to autocomplete an invalid command, so there may be a need 
      to perform checking of the command first, before letting the user know that the entered command is invalid.

#### \[Future Development\] Extension of Helper class to general commands

Currently, the helper class only aids users by prompting them with the necessary fields for the `add` command. This 
makes sense as the `add` command is the most complicated, involving the most number of fields and the most complex 
format. To a new user who is unfamiliar with the other commands, we can add more types of assistance to the helper 
class. The general helper class can prompt the user for the command they need help with. The user may enter "delete"
when they need help with the correct formatting of the `delete` command. The helper class can then prompt users for the 
necessary details needed for that command. 

Aside from adding more functionalities to the helper class, we can also implement command checking once the all the 
fields have been entered. As of now the `AddCommandHelper` does not check whether the details that are keyed in 
by the user are duplicate details. In the future iterations, we can implement a check that directly checks the details 
of the user once all of them have been entered.



### Duplicate feature

#### Implementation

The feature to be able to add persons with duplicate names in the address book are facilitated by the use of the
`DuplicateCommand`. It implements the following operations:
* `DuplicateCommand#`: Constructor class which is instantiated and stores the necessary `toAdd` person object
    based on user input.
* `DuplicateCommand#Executes`: Executes the necessary `addDuplicatePerson` method and updates the model.

The sorting mechanism consists of several components:
1. `addDuplicatePerson`: A method bound by the `Person`, `ModelManager`, `AddressBook` classes that each contain
    similar logic to support a SLAP form of implementation for the end execution point i.e. `execute` in 
    `DuplicateCommand`.
2. `DuplicateCommand`: Initiates the duplication by parsing user input to determine the identity of the person to add. 
    After duplicating, it then updates the list of persons in the model.

Given below is an example usage scenario and how the feature mechanism behaves at each step.

* Step 1: The user launches the application for the first time, no contacts will be present in the `AddressBook`.
  When user `add` contacts in the `AddressBook`, contacts will be sorted based on their timestamp.

* Step 2: The user reaches a point where they encounter the need to have to add a separate contact, that has the exact
  same name as another person in their `AddressBook`.

* Step 3: To continue, the user executes `add /n... /e ...` to attempt to add this new person.

* Step 4: The user then receives an error in their `AddressBook` which alerts them that they already have such a person
  in their `AddressBook`, and they have the option of overwriting the existing contact, or duplicating it.

* Step 5: The user picks their choice and edits the command in their current `CommandBox`, replacing `add` with either
  `duplicate` or `overwrite INDEX`, leaving the rest of the arguments untouched.

* Step 6: (1st case) The user executes `duplicate /n... /e...` command.
    * The `duplicateCommand#` constructor will initialize with the `toAdd` variable based on the created `Person` 
        object in `DuplicateCommandParser`.
    * `duplicateCommand#execute` will pass the `toAdd` to the `model#addDuplicatePerson`, where `UniquePersonsList`
        is updated with the duplicated person.
    * After duplicating, the model will be updated to reflect the newly sorted contacts list, 
        alongside a return statement to provide confirmation to the user.
  
* Step 6.1: (2nd case) The user executes `overwrite INDEX /n... /e...` command.
    * The `overwriteCommand#` constructor will initialize with the `toAdd` variable based on the created `Person`
      object in `OverwriteCommandParser`, as well as the user's inputted index of person to be edited in the 
      `AddressBook`.
    * `overwriteCommand#execute` will pass the `indexOfTarget` to the `model#getPerson`, and will also pass the `toAdd`
       to the `model#setDuplicatePerson`, where `UniquePersonsList` is updated with the duplicated person.

<puml src="diagrams/DuplicateSequenceDiagram.puml" width="450" />

<puml src="diagrams/OverwriteSequenceDiagram.puml" width="450" />
  
### Design consideration:
`SolidStrategy` interface was implemented to adhere to SOLID principles, particularly the Single Responsibility 
Principle and Interface Segregation Principle.
* Single Responsibility Principle
    * The class maintains single responsibility by defining methods for duplicating person strategies without burdening
      implementations with unrelated methods
* Interface Segregation Principle
    * Segregates behavior for sorting into distinct methods `addDuplicatePerson`, `setDuplicatePerson`, `getPerson`, 
      thus, allowing different sorting strategies to implement only the methods they need, rather than being forced to 
      implement monolithic interface with unnecessary methods.

* **Alternative 1** `DuplicateCommand` constructor of the `DuplicateCommand` to take in `toAdd` as its parameter.
    * Pros: Straightforward design and easy to implement.
        * Duplication logic interacts directly with data structure being sorted.

Alternative 1 is chosen for the following reasons:
* Simplicity: keeps duplicating logic simple and focused by directly interacting with the data structure being sorted.
* Clear Responsibility: Duplication logic is closely tied to the data structure it operates on, adhering to the Single
  Responsibility Principle.
* Ease of implementation: No need to pass unnecessary parameters to the DuplicateCommandParser method.
    * Reduce complexity and potential dependencies.

### Exit Window
#### Implementation
For this feature, an exit window [`ExitWindow`](https://github.com/AY2324S2-CS2103T-T12-2/tp/blob/master/src/main/java/seedu/address/ui/ExitWindow.java) is created to seeks confirmation from user to terminate LookMeUp. `ExitWindow` is packaged under `UI` , along with other various parts of Ui components e.g. `CommandBox`, `ResultDisplay`, and `PersonList` etc. Similar to other Ui components, `ExitWindow` inherits from `UiPart` which captures the commonalities between classes that represent the different part of the entire GUI.

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The Ui layout of `ExitWindow` is defined under [`ExitWindow.fxml`](https://github.com/AY2324S2-CS2103T-T12-2/tp/blob/master/src/main/resources/view/ExitWindow.fxml). Below elucidates how `ExitWindow` is used:
1. User executes the command `exit`, or other similar commands that resolves to `exit` deemed by the [fuzzy input algorithm](#fuzzy-input).
2. An exit window will appear prompting for user confirmation to exit - Yes/No button.
3. User would select either one of the 2 options.

<puml src="diagrams/ExitCommandActivityDiagram.puml" alt="Flow of Exit command"/>

In `ExitWindow.fxml`, the `Yes` button is set as the default button such that the button receives a VK_ENTER press; the `Yes` button will always be in focus whenever `ExitWindow` is displayed. When a positive confirmation is received, `ExitWindow#yesButton()` would be called to terminate LookMeUp.

Consequently, the `No` button is set as the cancel button where it would receive a VK_ESC press that hides `ExitWindow`. `ExitWindow#NoButton()` would be called when a negative confirmation is received.

The behaviour of these implementations follow the behaviours as specified by [JavaFx](https://openjfx.io/javadoc/11/javafx.controls/javafx/scene/control/Button.html).

#### Design Considerations
- Single Responsibility Principle
  - The `ExitWindow` maintains the responsibility of displaying exit confirmation and handling a user choice, which reduces coupling between itself and other Ui components.
  

- Alternate implementation: A text field input that requires user to enter yes/no for confirmation. This design was not conceived as it requires the handling of invalid input, as is not as simple to implement as compared to the current implementation. Moreover, confirmation utilizing buttons is more intuitive for majority of users.

### Input History Navigation
#### Implementation

This Ui feature allow users to restore previously entered commands typed in the [`CommandBox`](https://github.com/AY2324S2-CS2103T-T12-2/tp/blob/master/src/main/java/seedu/address/ui/CommandBox.java), regardless of the validity of the command. Similar to the CLI, users would use the Up/Down arrow keys to navigate previously typed commands in the input history.

The class that encapsulates all the history of the commands is `InputHistory` which is declared as a nested class inside `CommandBox`; this is because the history of commands should be the responsibility of `CommandBox` class and should not be openly accessible to other classes.

`InputHistory` is instantiated whenever the constructor of `CommandBox` is called. As such, there is an association between `InputHistory` and `CommandBox`. The implementation of `InputHistory` encapsulates an `ArrayList<String>` and an index-pointer. Whenever a command is received by `CommandBox`, the command typed will be stored inside `InputHistory` (regardless of validity), as shown by the code snippet below:

```Java
@FXML
public class CommandBox extends UiPart<Region> {
    
    ///Handles the event whenever a command is entered.
    @FXML
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText); //execute command in Logic
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        } finally {
            inputHistory.addToInputHistory(commandText); 
            commandTextField.setText(""); // clears the textfield
        }
    }
}
```

`CommandBox#handleArrowKey()` is called when a `KeyEvent` is detected by JavaFX event listener. With reference to the code snippet below, the function checks if `InputHistory` is empty. If the history is empty, it performs nothing. Else, it checks if whether the key pressed is an Up key, or a Down key. The code snippet below shows the implementation of `CommandBox#handleArrowKey()`:

```Java
private void handleArrowKey(KeyEvent event) {
        String keyName = event.getCode().getName();
        
        //Performs nothing if there is no history.
        if (inputHistory.inputList.isEmpty()) {
            return;
        }
        if (keyName.equals("Up")) {
            inputHistory.decrementIndex(); //Reduces pointer by 1
            setTextField(); // Sets textfield according to pointer
        }
        if (keyName.equals("Down")) {
            inputHistory.incrementIndex(); //Increment pointer by 1
            setTextField(); //Sets textfield according to pointer
        }
}
```

When `CommandBox#setTextField()` is called, it requests for the command from `InputHistory#getCommand()` that is pointed by the pointer, and sets the text field of `CommandBox` that is returned by the method.

How the `InputHistory` index-pointer works:
- Whenever a new command has been entered, the command is added into the list. The index-pointer is set to the **size** of the `ArrayList` (i.e. it is pointing towards an empty slot in the `ArrayList`).


- During an Up key press, the index-pointer is decremented by one (i.e. it is pointing towards an earlier command in the history).


- During a Down key press, the index-pointer is incremented by one (i.e. it is point towards a later command in the history).

#### Design Considerations
- Single Responsibility Principle 
  - `CommandBox` and `InputHistory` are gathered together as the two classes share the responsibilities of receiving and retrieving user inputs within the text field, hence increasing the overall cohesion of Ui components.
  

- `inputHistory` is set as a private variable as no other class should have access to the internal of the class, except `CommandBox` itself. This allows encapsulation and information-hiding from other classes. Setter and Getter methods of `InputHistory` such as `decrementIndex()`, `incrementIndex()` and `addToInputHistory()` etc. serve as functions to retrieve and modify the value of the class.


- Both `InputHistory#decrementIndex()` and `inputHistory#incrementIndex()` are designed with guard clauses to prevent the index pointer from reducing below zero or exceeding beyond the bounds of the `ArrayList<String>`.


- Alternative Design
  - Currently, the implementation of `InputHistory` consists of an `ArrayList<String>` that stores all previously typed commands. An alternative solution to using an ArrayList would be LinkedList. However, LinkedList is not adopted as Java's LinkedList is implemented as Doubly-linked list which causes more memory overhead than ArrayList. Moreover, due to regular access of elements in the collection, ArrayList is a better design decision as its `get` operation runs in constant time O(1), as compared to LinkedList `get` O(n). Other methods such as `remove` and `search` etc. were not considered in the design decision as these operations are not needed for implementing `InputHistory`, but may be relevant for future extensions to the class.


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:
NUS students who stay on campus

## User Stories
**Value proposition**:
1. Keeps track of the location and details specific to each contact, knowing who to make calls with
2. Given how students who stay on campus find themselves in many different committees and interest groups, our Address Book seeks to provide features that allows them to compartmentalise their contacts and access various groups easily


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                 | So that I can…​                                                        |
|----------|--------------------------------------------|------------------------------|------------------------------------------------------------------------|
| `* * *`  | Student in a lot of committees | Access my contacts by groups | Easily identify the people in their different committees and CCAs                 |
| `* * *`  | Student                                       | Sort the contacts alphabetically | Easily navigate the address book                                                        |


### Use cases

(For all use cases below, the **System** is `LookMeUp` and the **Actor** is the `user`, unless specified otherwise)

**Use case:** UC1 - Add a contact\
**Person that can play this role:** Student in a lot of committees

**MSS**

1. User type add contact command.
2. LookMeUp prompts for details.
3. User enters the requested details.
4. LookMeUp add the contact and displays the new contact in the database.\
    Use case ends.

**Extensions**
* 1a. User typed an invalid command
    * 1a1. LookMeUp displays the error.
    * 1a2. User enters the correct command.

  Steps 1a1-1a2 are repeated until the command entered is correct.\
  Use case resumes from step 2.

* 3a. LookMeUp detects an error in the entered data.
  * 3a1. LookMeUp displays the error and requests for the correct data.
  * 3a2. User enters the new data.

  Steps 3a1-3a2 are repeated until the data entered are correct.\
  Use case resumes from step 4.

**Use case:** UC2 - Remove a contact\
**Person that can play this role:** Student in a lot of committees

**MSS**

1. User type remove contact command
2. LookMeUp prompts for details
3. User enters the requested details
4. LookMeUp requests for confirmation.
5. LookMeUp removes the contact and displays an execution success message.\
   Use case ends.


**Extensions**
* 1a. User typed an invalid command
    * 1a1. LookMeUp displays the error.
    * 1a2. User enters the correct command.

  Steps 1a1-1a2 are repeated until the command entered is correct.\
  Use case resumes from step 2.


* 3a. LookMeUp detects an error in the entered data.
    * 3a1. LookMeUp displays the error and requests for the correct data.
    * 3a2. User enters the new data.

  Steps 3a1-3a2 are repeated until the data entered are correct.\
  Use case resumes from step 4.


* 4a. User declines the removal of contact.
    * 4a1, LookMeUp confirms user's selection.\
      Use case ends.

**Use case:** UC3 - Filter contacts by tags\
**Person that can play this role:** Student in a lot of committees

**MSS**

1. User type filter contacts command
2. LookMeUp displays the contact in the database\
Use case ends.

**Extensions**
* 1a. User typed an invalid command
    * 1a1. LookMeUp displays the error.
    * 1a2. User enters the correct command.

  Steps 1a1-1a2 are repeated until the command entered is correct.\
  Use case resumes from step 2.

**Use case:** UC4 - Sort contacts by tags\
**Actor:** User\
**Person that can play this role:** Student in a lot of committees

**MSS**

1. User type sort contacts command
2. LookMeUp displays the contact in the database\
Use case ends.

**Extensions**
* 1a. User typed an invalid command
    * 1a1. LookMeUp displays the error and shows a list of commands it supports.
    * 1a2. User enters the correct command.

  Steps 1a1-1a2 are repeated until the command entered is correct.\
  Use case resumes from step 2.

## Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  A user should be able to add contacts even if they are not IT-savvy.
5.  Any operation executed on the app (list, delete, add, etc) should not take more than 10 minutes to process.
6.  The startup time for the application should not take more than 10 minutes.
7.  Side pop-up windows should not interfere with the execution of commands in the main window.


## Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **IT-savvy**: The user is not familiar with the exact format of the add command.
* **Side pop-up window**: Additional windows that can be opened by the user during usage of the software(e.g. the help window).
* **SOLID principle**: The SOLID principle is a set of five design principles used in object-oriented programming to make software designs more understandable, flexible, and maintainable. The acronym SOLID stands for:
    * Single Responsibility Principle
    * Open/Closed Principle
    * Liskov Substitution Principle
    * Interface Segregation Principle
    * Dependency Inversion Principle
* **Levenshtein distance**: Measure of the difference between two strings, representing the minimum number of single-character edits (insertions, deletions, or substitutions) required to change one string into the other.
* **BK-Tree**: A tree data structure used to efficiently store and search for strings or other data based on their edit distance or similarity.



--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Add By Step 

Loading up the AddByStep Window

1. Type `addbystep` into LookMeUp
    * Expected output: A new window should appear, prompting you for the name of the person to enter 
2. Leave the name blank and press the ENTER key 
    * Expected output: An error message should appear, and you have to enter the name again 
3. Type `John` into the GUI 
    * Expected output: The name will be successfully accepted, and you will be prompted for the next field
4. You may follow the prompts to enter the subsequent details, examples of invalid inputs are given in the example use
case scenario in Add By Step.

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.


### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

### Planned Enhancements

Our team consists of 5 members. 

1. Currently, `AddCommandHelper` has to be closed manually, which is not optimised for fast typists. We plan to add a
   an exit command to `AddCommandHelper` such that you can close the window simply by typing the `exit` command

2. Fuzzy Input with varying distance metric
   * Currently, the MAX_DISTANCE for the distance metric is set to 1. 
   * To enhance user-experience and accommodate longer commands with potentially more misspellings, it would be advantageous to dynamically adjust the MAX_DISTANCE according
to the length of the correct command string. 
   * This approach allows a more flexible and adaptable matching process,
guaranteeing that the misspelling tolerance varies proportionately with command length. 
   * By dynamically adjusting the
MAX_DISTANCE, longer and more complex input command like `addbystep` can be accurately identified. 

