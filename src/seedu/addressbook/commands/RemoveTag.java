package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;
import seedu.addressbook.data.tag.UniqueTagList.TagNotFoundException;

public class RemoveTag extends Command{
	public static final String COMMAND_WORD="removetag";
	public static final String MESSAGE_USAGE=COMMAND_WORD+":\n"+
	"remove the tag from the address book. "+"Example: "+COMMAND_WORD+
	"frends";
	public static final String MESSAGE_SUCCESS="Tag deleted. ";
	private final Tag toDelete;
	public RemoveTag(String tagToDelete) throws IllegalValueException{		
			toDelete=new Tag(tagToDelete);	 
	}
	public CommandResult execute(){
		    removePersonWithTag();
			removeTagFromList();
			return new CommandResult(MESSAGE_SUCCESS);
	}
	private boolean removePersonWithTag(){
		boolean hasDeleted=false;
		for(ReadOnlyPerson person: addressBook.getAllPersons()){
			final UniqueTagList tags=person.getTags();
			if(tags.contains(toDelete)){
				try {
					tags.remove(toDelete);
				} catch (TagNotFoundException e) {
					e.printStackTrace();
				}
				hasDeleted=true;
			}
		}
		return hasDeleted;
	}
	private boolean removeTagFromList(){
		try {
			addressBook.removeTag(toDelete);
		} catch (TagNotFoundException e) {
			return false;
		}
		return true;
	}

}
