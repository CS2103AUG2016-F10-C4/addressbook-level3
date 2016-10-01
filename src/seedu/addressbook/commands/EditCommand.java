package seedu.addressbook.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.Address;
import seedu.addressbook.data.person.Email;
import seedu.addressbook.data.person.Name;
import seedu.addressbook.data.person.Person;
import seedu.addressbook.data.person.Phone;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.person.UniquePersonList;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

public class EditCommand extends Command {
	
	public static final String COMMAND_WORD = "edit";
	
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edit a person in the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: NAME [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS  [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "This person has been edited: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toEdit;
    
    public EditCommand(String name,
            String phone, boolean isPhonePrivate,
            String email, boolean isEmailPrivate,
            String address, boolean isAddressPrivate,
            Set<String> tags) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : tags) {
		  tagSet.add(new Tag(tagName));
		}
		this.toEdit= new Person(
		      new Name(name),
		      new Phone(phone, isPhonePrivate),
		      new Email(email, isEmailPrivate),
		      new Address(address, isAddressPrivate),
		      new UniqueTagList(tagSet)
		);
	}
    
	@Override
	public CommandResult execute() {
		try {
			addressBook.addPerson(toEdit);
			ReadOnlyPerson toRemove = null;
			for (ReadOnlyPerson person : addressBook.getAllPersons()) {
				if(person.getName().equals(toEdit.getName()))
					toRemove = person;
			}
            addressBook.removePerson(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        } catch (UniquePersonList.DuplicatePersonException dpe) {
            return new CommandResult(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
        	return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
		}
	}

}
