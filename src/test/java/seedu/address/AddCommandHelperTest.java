package seedu.address;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.ui.Status;

public class AddCommandHelperTest {


    @Test
    public void setStatus_from_name_to_number_success() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid name";
        }
        assertEquals(a.getStatus(), Status.GET_NUMBER);

    }

    @Test
    public void setStatus_from_name_to_number_fail() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid name";
        }
        assertEquals(a.getStatus(), Status.GET_NUMBER);

    }



    @Test
    public void setStatus_from_number_to_email_success() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
            a.getResponse("12345678");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid number";
        }
        assertEquals(a.getStatus(), Status.GET_EMAIL);

    }

    @Test
    public void setStatus_from_email_to_address_success() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
            a.getResponse("12345678");
            a.getResponse("r@gmail.com");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid email";
        }
        assertEquals(a.getStatus(), Status.GET_ADDRESS);

    }

    @Test
    public void setStatus_from_address_to_tag_success() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
            a.getResponse("12345678");
            a.getResponse("r@gmail.com");
            a.getResponse("Bishan St 39");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid address";
        }
        assertEquals(a.getStatus(), Status.GET_TAG);

    }

    @Test
    public void setStatus_from_tag_to_complete_success() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
            a.getResponse("12345678");
            a.getResponse("r@gmail.com");
            a.getResponse("Bishan St 39");
            a.getResponse(" ");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid tag";
        }
        assertEquals(a.getStatus(), Status.COMPLETE);

    }

    @Test
    public void setStatus_from_complete_to_complete_success() {
        AddCommandHelper a = new AddCommandHelper();
        try {
            a.getResponse("Jack");
            a.getResponse("12345678");
            a.getResponse("r@gmail.com");
            a.getResponse("Bishan St 39");
            a.getResponse(" ");
            a.getResponse("input here does not  matter");
        } catch (ParseException e) {
            assert false : "No exception should be thrown for valid name";
        }
        System.out.println(a.getStatus());;
        assertEquals(a.getStatus(), Status.COMPLETE);

    }





}
