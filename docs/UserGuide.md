---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# LookMeUp User Guide

Greetings! Students from the Halls of NUS, are you tired of the hassle of managing your contacts? 

Welcome to LookMeUp, your one-stop desktop app that revolutionizes contacts management for NUS students like YOU, with 
multiple commitments and multiple groups of friends to keep track of! 
Liking the speed and effectiveness of Command Line Interface (CLI) or visual simplicity of Graphical User 
Interface (GUI)? LookMeUp caters to your needs, it ensures that managing your contacts is quicker and more efficient 
than ever before.

So say goodbye to traditional address book applications and say hello to the future of contact management with LookMeUp!

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `LookMeUp.jar` from [here](https://github.com/AY2324S2-CS2103T-T12-2/tp/releases/tag/v1.3(final)).

1. Copy the file to the folder you want to use as the _home folder_ for the LookMeUp app.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar LookMeUp.jar` command 
   to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   <img src="images/Ui.png" width="50%"/>

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will 
   open the help window.<br>

1. For users who are familiar with our app, or simply wish to get a quick reference to our commands, feel free to refer  
   to the [Command Summary](#command-summary) below! Else, you can refer to the [Features](#features) section for a
   detailed explanation of each command.

--------------------------------------------------------------------------------------------------------------------

## Features


<box type="info" seamless>

**Syntax of Commands:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

> [!TIP]
> * LookMeUp supports **fuzzy commands** with a maximum allowance of 1 misspelled letter,
    preventing users from needing to retype the entire command due to a single spelling mistake.
  >   * Examples:
  >     * `swot` will be interpreted as `sort`
>     * `addystep` will be interpreted as `addbystep`
> * LookMeUp text field supports **command history** accessibility.
    >   * You can make use of your `Up` and `Down` arrow keys to navigate through the commands that you have previously entered.

> [!WARNING]
> Command prefixes (n/…​ a/…​ p/…​ e/…​ t/…​) only accepts lower case characters.
>   * Examples like N/…​ A/…​ P/…​ E/…​ T/…​ where prefixes are capital letters will not be accepted.

### Viewing help : `help`

<img src="images/Ui-help.png" width="50%"/>

Shows a message explaining how to access the help page.

<img src="images/helpMessage.png" width="50%"/>

Format: `help`


### Adding a person (Full Command): `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

> [!TIP]
> 
> * A person can have any number of tags **(including 0)**.
> 
> * There are no character limit restrictions for each input.
>   * However, it is advisable to keep each field under **100 characters** to ensure compatibility with your device's resolution.

> [!IMPORTANT]
>
> * Phone numbers should only contain numbers **(min 3 numbers)**.
> 
> * LookMeUp only supports **alphanumeric characters** for name, address, email and tag inputs 
>   (with email accepting a single `@`).

> [!NOTE]
> 
> **Why are there alphanumeric restrictions on the Address, Email and Tag inputs, and how does it help YOU?**
> 
> All NUS student emails are restricted to alphanumeric characters as with both the default NUSNET email 
> and the FriendlyMail guidelines. This restriction ensures additional safety that the email entered is an NUS student 
> email, catching any accidental typos of non-alphanumeric characters. 
> 
> Similarly, all addresses and tags (in the context of classifying by interest groups, committees etc.) are
> expected to be alphanumeric, thus serves as another safety net.



Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

> [!NOTE]
> * Do be careful when you are adding a new contact, as extra spacing could lead to a similar or identical name
being recognized as a new, unique name. e.g. John Doe is not the same as JohnDoe
> * Similarly, names are also case-sensitive, so do be careful when entering your contact's name as well. 
e.g. John Doe is treated differently from John doe

### Adding a person (With System Prompts): `addbystep`

<img src="images/AddByStep.png" width="40%"/>

To streamline the process of adding contacts, `addbystep` command offers user-friendly interface that prompts you for each required field
in the address book entry.<br/>
While this simplifies the data entry process, you will still need to manually copy `cp` and paste the final result into the command box.

Format: `addbystep`

> [!NOTE]
> * If you enter `addbystep` with any additional parameters, _e.g. `addbystep 123`_, the additional parameters 
will be ignored, and `addbystep` window will still launch as per normal.
> * Currently, `addbystep` does not support the filling of tags when adding a new contact.
> * Once you have added all the details, you have to close the window and retype the command to create a `add` command
> * Since this is an accessory window, **maximising of the window is not supported**.
> * `addbystep` Only helps you to format the command correctly, it does not help to check if the person that you are 
adding is a duplicate. You have to copy the command to your clipboard and paste it into LookMeUp to verify if the 
person is non-duplicate.


### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* The index provided must be within the range of available contacts in the address book.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

> [!NOTE]
> 
> Editing a contact with the same value will still be considered a successful edit, and LookMeUp will prompt a "successful" message.
> 
> LookMeUp will display the entire contact fields in the "successful" message (shown below).
> 
> <img src="images/successEdit.png" width="100%"/>


### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
<img src="images/findAlexDavidResult.png" width="50%"/> <br>

### Removing a person (Safe Removal): `remove`

Removes a person based on index, and confirms removal of the spotlighted contact before actual removal.

Format in 2 steps:

1. `remove INDEX`<br>
   example: `remove 3`<br>
   <img src="images/index-remove.png" width="50%"/><br>
   * Removes the contact from the specified index.
   * The `INDEX` refers to the index number shown in the displayed person list.
   * The index must be a `positive integer` 1, 2, 3, … and can only be as large as the index of the last contact in 
   the current list (be it the default or filtered).
     * LookMeUp then "spotlights" (only shows) this one contact, and prompts a confirmation message to confirm removal<br>
       <img src="images/confirmation.png" width="50%"/><br>


2. Confirmation: `yes/no`<br>
   1. If `yes`:<br>
      
      Expected result: <br>
      <img src="images/yes-result.png" width="50%"/><br> 
   2. If `no`:<br>
   
      Expected result: <br>
      <img src="images/no-result.png" width="50%"/><br>

> [!IMPORTANT] 
> 
> **How to deal with wrong/unknown command(s) entered in between the workflow of `remove INDEX` and `yes`/`no` 
> confirmation?**
> 
> > **NOTE:**
> > LookMeUp will **NOT** return to the default list upon this invalid command entry, due to uncertainty of whether
>    user wishes to continue with removal process or change to perform another command!
>
>_Some common scenarios:_ <br>
> 1. **If it was a mere mistake, and you still wish to continue on with the contact removal process:**<br>
   simply enter `remove 1` again, it serves as a __safety check__ telling LookMeUp that you ***still*** wish to remove 
> the current shortlisted contact, and then proceed with `yes` / `no` confirmation
> 2. **If you wish to stop the removal process and return to the default list:**<br>
   simply enter `list` to return to the default list, and then proceed with your next desired command


> [!TIP] 
> 
> **What if you wish to remove a different contact or execute a different command, 
> after the `remove INDEX`, before `yes` / `no` confirmation?**
> 
> Although you are ALLOWED to enter other valid commands in between the `remove INDEX` and the `yes` / `no` confirmation 
> (e.g. `remove 1`, then when prompted for confirmation, proceed with keying in separate command `addbystep` and then 
> execute the `add` command), it is **ADVISABLE** to enter `no` or `list` first after `remove 1`.
> 
> This is especially so if you intentionally wish to **leave the removal process**, since these 2 commands would bring 
> you back to the existing default list of contacts! 
> 
> This will then allow list-accessing commands (e.g. `edit` or `remove` if you 
> wish to remove a different contact) to access all existing contacts, rather than being limited to the single filtered 
> ("spotlighted") contact (after `remove INDEX`) since that was meant for the confirmation of the safe removal.  

> [!TIP] 
>
> **How to potentially _SPEED UP_ the contact removal process, especially when LookMeUp gets populated with MANY 
> contacts?**
> 
> Make use of the contact filtering feature of `find` and use it together with the `remove` commands! 
> 
> For example: `find rachel` (which shortlists all contact(s) matching "rachel") followed by `remove 1` will prompt a 
> confirmation message to confirm the removal of the 1st contact in the filtered results of the `find` command. 
> 
> This is especially useful if you wish to remove a contact without having to scroll for its index, or if you have
> multiple contacts with the same part(s) of a name and wish to shortlist e.g. all "rachel"s first before deciding which
> to remove by the index of the filtered list. 

### Undo Previous Command : `undo`

For any command that changes the universal list of contacts _e.g. `add`, `remove`, `clear`, `overwrite`, `duplicate` and `edit`_,
the `undo` command will revert the state of the contact list prior to the execution of a command. 

> [!NOTE]
> LookMeUp supports up to 3 consecutive `undo`
>   * You are able to backtrack your actions up to 3 times. 

Format: `undo`
For example, referring to the previous command, assuming you have `removed` a contact, you can type `undo` to recover the removed contact:<br>
<img src="images/undo.png" width="50%"/><br>

The removed contact will then be restored, **even to its original index**.<br>
<img src="images/recovered.png" width="50%"/><br>

Similarly, `undoing` after adding a contact would mean reverting the contact list's state back to before the contact was added.

> [!IMPORTANT]
> Once you closed the application, all your changes will be saved and all your past command history will be **erased**.
>   * That is, when you launch the app again, you will not be able to undo any commands from the previous time you launched it.

###  Redo Undid Command : `redo`

Redo the most recent `undo` command.

Format: `redo`

For example, entering `redo` after previous `undo` example will revert the contacts to before `undo` was being executed.

> [!IMPORTANT]
> `redo` only works when `undo` was called.
> If there were no commands undone, entering `redo` will prompt an **error**.

### Copies a Person Information to Clipboard : `copy`

Copies a person’s information such as name, phone number, address and email into your OS clipboard. 
This feature allows you to copy **more than one** piece of a contact’s information, and allows you to specify the order of a person’s information to be copied. 
If multiple fields are provided, results are separated by a single whitespace.

> [!NOTE]
> Duplicated fields that are specified are safely process and copied 
> * Refer to the table below 

Format: `copy INDEX FIELD(s)`

Example:<br>
<img src="images/example.png" width="50%"/><br>

Based on the sample contact above:

| Sample Commands       | Details                                                                    | Results                  |
|-----------------------|----------------------------------------------------------------------------|--------------------------|
| `copy 4 name`         | Copies the name of contact indexed 4                                       | `Bert`                   |
| `copy 4 name address` | Copies the name and address of contact indexed 4                           | `Bert Sesame Street`     |
| `copy 4 phone email`  | Copies the phone and email of contact indexed 4                            | `88891234 Bert@gmail.com` |
| `copy 4 email email`  | Copies the email of contact indexed 4  **(Duplicated fields are ignored)** | `Bert@gmail.com`         |
| `copy 4 nnamee phone` | Incorrect field detected                                                   | `N.A.`                   |

### Sorting the Contacts : `sort`

Sorts the entries in the address book based on the given condition.

Format: `sort KEYWORD`

* LookMeUp supports the following `KEYWORDs` : `name`, `tag`
    * `Name`: Sorts the entries based on lexicographical order of names.
    * `Tag`: Sorts the entries based on lexicographical order of tags.

> [!NOTE]
> When `sort tag` is executed, LookMeUp sorts tags by 
> **numbering**, followed by contacts **without tags**, and finally **alphabetically**
> 
>    <img src="images/savetag.png" height="50%"/><br>

### Filtering by Tag : `filter`

Shows a list of persons in the address book, filtered by `specified tag`.

Format: `filter TAGNAME`

Example: `filter 13`

### Adding a Contact with Duplicate Identity : `duplicate`

Adds the new contact to the address book, **assuming that a contact with an identical name already exists**.

Format: `duplicate n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

Example:
Say you have a list of contacts like the following, and you wish to add a contact with an **identical name** to the first entry `Alex Yeoh`<br>
<img src="images/sample.png" width="50%"/><br>

You will encounter the following error using the `add` command<br>
<img src="images/error.png" width="50%"/><br>

To duplicate the contact, run the following `duplicate` command and enter to see the results.<br>
<img src="images/duplicate-command.png" width="50%"/><br>
<img src="images/duplicate-success.png" width="50%"/><br>

### Overwriting an Existing Contact : `overwrite`

Overwrites an existing contact in the address book, provided that a contact with an identical identity already exists in the address book.

Format: `overwrite INDEX n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`
* `INDEX` refers to the index number shown in the displayed person list, that represents the target contact to be overwrite.
* The index **must be positive integer** 1, 2, 3, …​

Example:
Say you tried to add a contact with an **identical name** to the first entry `Alex Yeoh`<br>
<img src="images/sample.png" width="50%"/><br>

You will encounter the following error using the `add` command<br>
<img src="images/error.png" width="50%"/><br>

In the case where you actually intended to overwrite the contact instead, run the following `overwrite` command and enter to see the results.<br>
<img src="images/overwrite-example.png" width="50%"/><br>
<img src="images/overwrite-success.png" width="50%"/><br>

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

A pop-up would be shown that prompts you for **confirmation** to exit the address book.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Command                                        | Details                                                                                                                                                                                                                                  |
|------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `list`                                         | List all contacts                                                                                                                                                                                                                        |
| `add n/…​ p/…​ e/…​ a/…​ [t/TAG]…​`            | Adds a contact into the Address Book.<br/>**Example:**<br/>`add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`                                                                                              |
| `remove INDEX`<br/>`yes/no`                    | Safe removal of contact based on the index of the contact keyed in, followed by confirmation step before actual removal.                                                                                                                 |
| `undo`                                         | Undo the previous command entered.                                                                                                                                                                                                       |
| `redo`                                         | Reverses the previous `undo` command.                                                                                                                                                                                                    |
| `copy INDEX FIELD(s)`                          | Copies a contact's information e.g. name, phone, email and address into OS clipboard.                                                                                                                                                    |
| `sort KEYWORD`                                 | Sorts contacts based on the input condition.<br/>**KEYWORDS:**<br/>`NAME`,`TAG`                                                                                                                                                          |
| `filter TAGNAME`                               | Shows a list of persons in the address book, filtered by the specified tag.<br/>**Example:**<br/>`filter friends`                                                                                                                        |
| `duplicate n/…​ p/…​ e/…​ a/…​`                | Adds the new contact to the address book, **assuming that a contact with identical identity already exists**.<br/>**Example:**<br/>`duplicate n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`                |
| `overwrite INDEX n/…​ p/…​ e/…​ a/…​ [tTAG]…​` | Overwrites an existing contact in the address book, **assuming that a contact with an identical identity already exists**.<br/>**Example:**<br/>`overwrite 2 n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` |
| `clear`                                        | Deletes all contacts                                                                                                                                                                                                                     |
| `exit`                                         | Exits and closes the program.                                                                                                                                                                                                            |

--------------------------------------------------------------------------------------------------------------------

## Glossary

### BK-Tree
* A tree data structure used to efficiently store and search for strings or other data based on their edit distance or similarity.

### Lexicographical
* Order of words based on the alphabetical order of their letters.
