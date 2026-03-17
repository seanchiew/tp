---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](http://se-education.org)

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

Classes used by multiple components are in the `seedu.address.commons` package.

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

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


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

* requires a lightweight way to manage a significant number of concurrent internship applications
* tech-savvy undergraduate
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**:

* manage internship applications faster than a typical mouse/GUI driven app
* helps user capture, update, and retrieve key application details in seconds
* reduce missed deadlines and mental overhead while managing concurrent applications
* serve as a lightweight offline desktop tool


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​ | I want to …​ | So that I …​ |
|----------|---------|--------------|----------------|
| `* * *` | undergraduate applicant | add a new application-related contact record | can track important contacts instead of relying on memory |
| `* * *` | busy applicant juggling many applications | list all tracked contact records | can see who I am currently managing at a glance |
| `* * *` | busy applicant juggling many applications | remove a contact record I no longer need | my tracker stays uncluttered |
| `* * *` | undergraduate applicant | have my tracked records persist between sessions | do not lose critical contact information |
| `* * *` | undergraduate applicant | have each contact record include opportunity context such as company and role | can remember why the contact matters |
| `* * *` | busy applicant juggling many applications | edit an existing contact record | can keep details up to date when a contact’s information or application context changes |
| `* * *` | busy applicant juggling many applications | quickly find a contact record by keyword | can retrieve details under time pressure |
| `* *` | new user exploring InternTrack | see example records | can understand what information the product is meant to track |
| `* *` | new user exploring InternTrack | remove the example records in one go | can start clean without tedious manual cleanup |
| `* *` | busy applicant juggling many applications | record a stage or status for a contact record | know my current progress with that contact and opportunity |
| `* *` | undergraduate applicant | store contact details such as name and email | can follow up without searching through chats or email threads |
| `* *` | undergraduate applicant | store a contact role for each record | can tell whether someone is a recruiter, interviewer, referrer, or hiring manager |
| `* *` | busy applicant juggling many applications | record a deadline for a contact record | can keep track of upcoming follow-ups, interviews, or submission-related actions |
| `* *` | busy applicant juggling many applications | view only records in a chosen stage | can batch-handle similar follow-ups |
| `* *` | busy applicant juggling many applications | view records ordered by upcoming deadlines | know what to prioritize next |
| `* *` | busy applicant juggling many applications | remove multiple contact records at once | can clean up outdated records more efficiently |
| `* *` | end-of-cycle user | archive old records | my current list stays focused without losing history |
| `* *` | end-of-cycle user | unarchive previously archived records | can restore older records when I need to revisit them |
| `* *` | user resetting my tracker | clear all contact records in one shot | can start over without deleting records one by one |
| `* *` | new user exploring InternTrack | have quick access to the user guide from within the app | can learn how to use the commands when I get stuck |
| `* *` | keyboard-oriented user | exit the app using a command | can close it quickly without leaving the keyboard |
| `* *` | busy applicant juggling many applications | filter records by company | can focus on all contacts related to one organization at a time |
| `*` | undergraduate applicant | tag or classify contact records | can separate different kinds of contacts or opportunities quickly |
| `*` | undergraduate applicant | store short notes for a contact record | can remember context such as referrals, follow-up points, or interview details |
| `*` | busy applicant juggling many applications | be warned about potential duplicate contact records | do not track the same contact twice unnecessarily |
| `*` | frequent user | maintain consistent stage labels | filtering and review remain reliable over time |
| `*` | frequent user | extract key contact information from selected records | can paste it into an email client quickly |
| `*` | end-of-cycle user | review outcomes by cycle | can improve my application strategy next time |
| `*` | frequent user | search within notes | can quickly retrieve context like “referral,” “visa,” or “follow-up” |
| `*` | frequent user | mark records that require follow-up | do not forget to take action |
| `*` | busy applicant juggling many applications | recover from accidental destructive actions | one mistake does not wipe critical records |
| `*` | frequent user | sort records by company or role | can review my contacts in a more organized way |
| `*` | busy applicant juggling many applications | view only records that require follow-up soon | can quickly identify the contacts that need action next |
| `*` | end-of-cycle user | export my contact records for a cycle | can keep an external backup or review them outside the app later |
| `*` | frequent user | filter records by contact role | can focus separately on recruiters, interviewers, referrers, or hiring managers |
| `*` | frequent user | view a summary of how many records are in each stage | can quickly understand the overall state of my application-related contacts |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `InternTrack` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 — Add an opportunity record**

**MSS**

1. User requests to add a new opportunity contact.
2. System validates the provided details.
3. System creates the new opportunity contact.
4. System reflects the newly added opportunity contact.

   Use case ends.

**Extensions**
* 2a. Required details are missing (Name, Email, Contact Role, Company, Role, or Status).
    * 2a1. System shows an error message indicating missing required details.

        Use case resumes from step 1.

* 2b. Duplicate detected (same Company and Role already exists).
    * 2c1. System informs the user that a duplicate opportunity contact exists.

        Use case ends.

* 2c. Provided details are invalid (e.g. Email is not a valid format, Status is not a recognised value).
    * 2c1. System shows an error message indicating the invalid field(s).

        Use case resumes from step 1.


**Use case: UC02 — Remove an opportunity record**

**MSS**

1.  User requests to <u>list opportunity records (UC3)</u>.
2.  System shows the list of stored opportunity records.
3.  User requests to remove one or more specific records in the list.
4.  System removes the requested record(s).
5.  System reflects the removal.

    Use case ends.

**Extensions**

* 1a. No records exist.
    * 1a1. System informs the user there are no records to remove.

      Use case ends.

* 3a. One or more of the provided indexes does not refer to an existing record.
    * 3a1. System shows an error message.

      Use case resumes from step 3.


**Use case: UC03 — List all opportunity records**

**MSS**

1.  User requests to list all opportunity records.
2.  System shows the list of stored opportunity records.

    Use case ends.

**Extensions**

* 1a. No records exist.
    * 1a1. System informs the user that the list is empty.

      Use case ends.


**Use case: UC04 — Edit an opportunity contact**

Preconditions: At least one record exists.

**MSS**

1.  User requests to <u>list opportunity contacts (UC3)</u>.
2.  System shows the list of opportunity contacts.
3.  User requests to edit the details of a specified opportunity contact.
4.  System validates the new details.
5.  System updates the opportunity contact.
6.  System reflects the update.

    Use case ends.

**Extensions**

* 4a. Index is invalid.
  * 4a1. System shows an error message.

    Use case resumes from step 3.

* 4b. Provided details are invalid (e.g. Email is not a valid format, Status is not a recognised value).
  * 4b1. System shows an error message indicating the invalid field(s).

    Use case resumes from step 3.

**Use case: UC05 — Search for opportunities by keyword**

**MSS**

1.  User requests to search opportunity records matching a keyword.
2.  System shows all records that match the keyword.

    Use case ends.

**Extensions**

* 2a. No matches found.
    * 2a1. System informs the user there are no matching records.

      Use case ends.

**Use case: UC06 — Archive an application cycle**

**Preconditions:** At least one record exists in the current cycle.

**MSS**

1. User requests to archive the current cycle.
2. System archives the current cycle and starts a new empty cycle.
3. System confirms that the new cycle is active and the archived cycle remains accessible.

   Use case ends.


**Extensions**

* 1a. No records exist.
    * 1a1. System informs the user there is nothing to archive.

      Use case ends.

* 2a. Archive fails due to storage write failure.
    * 2a1. System informs the user the archive operation failed.

      Use case ends.

**Use case: UC09 — Clear record**

**MSS**

1. User requests to clear all contact records.
2. System deletes all tracked opportunity data, replacing it with an empty state.
3. System reflects an empty tracker.

   Use case ends.

**Use case: UC10 — Request for help**

**MSS**

1. User requests for help.
2. System opens the help window containing a link to the User Guide.
3. User copies link to User Guide.

   Use case ends.

**Use case: UC11 — Exit the application**

**MSS**

1. User requests to exit the application.
2. System saves any unsaved data.
3. System closes the application. 

   Use case ends.

### Non-Functional Requirements

1. **Platform compatibility:** The app must run on Windows 10/11, macOS, and Ubuntu Linux with Java 17 installed.
2. **Distribution:** The app must be distributable as a single executable JAR without an installer.
3. **Offline-first:** All core functions must work fully offline and must not require any remote server or external API.
4. **Local storage format:** Data must be stored locally in a human-editable text file (e.g., JSON) and must not use a DBMS.
5. **Response time:** With up to **500** opportunity records, typical commands (add, delete, list, find, update) must complete within **500 ms**, excluding application startup.
6. **Startup time:** With up to **500** records, the application should be usable within **2 seconds** after launch on a typical modern laptop.
7. **Autosave reliability:** The app must automatically save after every state-changing operation (e.g., add, remove, update).
8. **Graceful storage failure:** If reading or writing the storage file fails, the app must not crash and must show a clear error message.
9. **CLI-first usability:** All core functions must be operable using keyboard-only input; the GUI is for visualization and must not be required to complete core tasks.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **CLI**: Command Line Interface; users interact primarily by typing commands.
* **Opportunity record (Record)**: A single entry representing one internship application being tracked.
* **Company**: The organisation that offers the opportunity.
* **Role**: The position/title applied for.
* **Status / Stage**: The current progress state of an application (e.g., SAVED, APPLIED, OA, INTERVIEW, OFFER, REJECTED, WITHDRAWN).
* **Deadline**: The date/time by which a submission or action is due for an opportunity record.
* **Outcome**: A terminal result of an application (e.g., OFFER, REJECTED, WITHDRAWN).
* **Keyword**: A text fragment used to search/filter records.
* **Duplicate opportunity**: Two records that refer to the same opportunity under the project’s duplicate rule (e.g., same Company and Role).
* **Index**: A temporary 1-based position number shown in a displayed list.
* **MSS**: Main Success Scenario; the most straightforward interaction for a given use case, which assumes that nothing goes wrong.
* **Persistence**: The ability to save and load data across sessions from local storage.
* **Prefix**: A short label used to indicate a field in typed input (e.g., c/, r/).
* **Cycle**: A time-bounded application period (e.g., one internship season) that can later be archived and reset.

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

1. _{ more test cases …​ }_

### Deleting an opportunity

1. Deleting an opportunity while all opportunities are being shown

   1. Prerequisites: List all opportunities using the `list` command. Multiple opportunities in the list.

   1. Test case: `delete 1`<br>
      Expected: First opportunity is deleted from the list. Details of the deleted opportunity shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No opportunity is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
