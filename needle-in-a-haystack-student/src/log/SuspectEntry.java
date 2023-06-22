/*
 * Copyright 2021 Marc Liberatore.
 */

package log;

import javax.security.auth.Subject;
import java.util.Objects;

public class SuspectEntry implements Comparable<SuspectEntry> {
	private String name;
	private String phoneNumber;
	private String passportNumber;

	public SuspectEntry(String name, String phoneNumber, String passportNumber) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.passportNumber = passportNumber;
	}

	public String getPassportNumber() {
		String x = this.passportNumber;
		return x;
	}

	public String getPhoneNumber() {
		String x = this.phoneNumber;
		return x;
	}

	public String getName() {
		String x = this.name;
		return x;
	}

	/// why the fuck did i need to do this?

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		SuspectEntry other = (SuspectEntry) obj;

		return Objects.equals(this.name, other.name)
				&& Objects.equals(this.phoneNumber, other.phoneNumber)
				&& Objects.equals(this.passportNumber, other.passportNumber);
	}

	/// why did i need to write this to not have two identical entries get into the
	/// same set
	@Override
	public int hashCode() {
		return Objects.hash(name, phoneNumber, passportNumber);
	}

	@Override
	public int compareTo(SuspectEntry other) {
		// Compare passport numbers first
		int passportComparison = this.passportNumber.compareTo(other.passportNumber);
		if (passportComparison != 0) {
			return passportComparison;
		}

		// If passport numbers are equal, compare names
		int nameComparison = this.name.compareTo(other.name);
		if (nameComparison != 0) {
			return nameComparison;
		}

		// If names are equal, compare phone numbers
		return this.phoneNumber.compareTo(other.phoneNumber);
	}

	// Other methods, if any...
}
