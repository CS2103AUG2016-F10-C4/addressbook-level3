package seedu.addressbook.commands;

import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.ReadOnlyPerson;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

import java.util.*;

/**
 * Finds and lists all persons in address book whose tags contains some of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command{

    public static final String COMMAND_WORD = "find-tag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Finds all persons whose tags contain some of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers.\n\t"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n\t"
            + "Example: " + COMMAND_WORD + " friend CCA";

    private final Set<String> keywords;

    public FindTagCommand(Set<String> keywords) {
        this.keywords = keywords;
    }

    /**
     * Returns copy of keywords in this command.
     */
    public Set<String> getKeywords() {
        return new HashSet<>(keywords);
    }

    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> personsFound;
        try {
            personsFound = getPersonsWithTagContainingAllKeyword(keywords);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage()).execute(); 
        }
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieve all persons in the address book whose tags contain some of the specified keywords.
     *
     * @param keywords for searching
     * @return list of persons found
     * @throws IllegalValueException 
     */
    private List<ReadOnlyPerson> getPersonsWithTagContainingAllKeyword(Set<String> keywords) throws IllegalValueException {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final UniqueTagList tags = person.getTags();
            for (String k : keywords) {
                Tag tag = new Tag(k);
                if (tags.contains(tag)) {
                    matchedPersons.add(person);
                    break;
                }
            }
        }
        return matchedPersons;
    }

}
