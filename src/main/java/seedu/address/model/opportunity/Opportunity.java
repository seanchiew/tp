package seedu.address.model.opportunity;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Opportunity in the tracker. Guarantees: required fields are
 * present and not null, optional fields may be absent, field values are
 * validated by their respective classes, immutable.
 */
public class Opportunity {

    // Required fields
    private final Name name;
    private final Email email;
    private final ContactRole contactRole;
    private final Company company;
    private final Role role;
    private final Status status;
    private final Cycle cycle;
    private final boolean isArchived;

    // Optional fields
    private final Phone phone;

    /**
     * Required fields must be present and not null. Phone is optional and may
     * be null.
     */
    public Opportunity(Name name, Email email, ContactRole contactRole, Company company, Role role, Status status,
                                    Cycle cycle, boolean isArchived, Phone phone) {
        requireAllNonNull(name, email, contactRole, company, role, status, cycle);
        this.name = name;
        this.email = email;
        this.contactRole = contactRole;
        this.company = company;
        this.role = role;
        this.status = status;
        this.cycle = cycle;
        this.isArchived = isArchived;
        this.phone = phone;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public ContactRole getContactRole() {
        return contactRole;
    }

    public Company getCompany() {
        return company;
    }

    public Role getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public boolean isArchived() {
        return isArchived;
    }

    /**
     * Returns an {@code Optional} containing the phone number, or an empty
     * Optional if absent.
     */
    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    /**
     * Returns true if both opportunities represent the same contact-opportunity
     * relationship, defined by Email, Company, Role, and Cycle.
     *
     * <p>Email, Company, and Role are compared case-insensitively so that
     * records differing only in capitalisation (e.g. "Google" vs "google")
     * are treated as duplicates. Cycle is compared as-is because Cycle values
     * are already normalised to uppercase by the parser before storage.
     *
     * <p>This defines a weaker notion of equality than {@link #equals(Object)},
     * which performs strict field-by-field comparison.
     */
    public boolean isSameOpportunity(Opportunity otherOpportunity) {
        if (otherOpportunity == this) {
            return true;
        }

        return otherOpportunity != null
                && otherOpportunity.getEmail().value.equalsIgnoreCase(getEmail().value)
                && otherOpportunity.getCompany().companyName.equalsIgnoreCase(getCompany().companyName)
                && otherOpportunity.getRole().roleName.equalsIgnoreCase(getRole().roleName)
                && otherOpportunity.getCycle().equals(getCycle());
    }

    /**
     * Creates and returns a copy of this opportunity with its archive status
     * set to true.
     */
    public Opportunity archive() {
        return new Opportunity(name, email, contactRole, company, role, status, cycle, true, phone);
    }

    /**
     * Creates and returns a copy of this opportunity with its archive status
     * set to false.
     */
    public Opportunity unarchive() {
        return new Opportunity(name, email, contactRole, company, role, status, cycle, false, phone);
    }

    /**
     * Returns true if both opportunities have the same identity and data
     * fields. This defines a stronger notion of equality between two
     * opportunities.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Opportunity)) {
            return false;
        }

        Opportunity otherOpportunity = (Opportunity) other;
        return name.equals(otherOpportunity.name) && email.equals(otherOpportunity.email)
                                        && contactRole.equals(otherOpportunity.contactRole)
                                        && company.equals(otherOpportunity.company)
                                        && role.equals(otherOpportunity.role) && status.equals(otherOpportunity.status)
                                        && cycle.equals(otherOpportunity.cycle)
                                        && isArchived == otherOpportunity.isArchived
                                        && Objects.equals(phone, otherOpportunity.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, contactRole, company, role, status, cycle, isArchived, phone);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name", name).add("email", email).add("contactRole", contactRole)
                                        .add("company", company).add("role", role).add("status", status)
                                        .add("cycle", cycle).add("phone", phone).toString();
    }

}
