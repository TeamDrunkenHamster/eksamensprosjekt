package logic;

import com.ferrari.finances.dk.bank.InterestRate;
import com.ferrari.finances.dk.rki.CreditRator;

public class APIImpl implements API {

	private String creditRate;
	private double bankRate;

	@Override
	public String getCreditRate(String cpr) {

		Thread creditRateThread = new Thread() {

			@Override
			public void run() {

				creditRate = getCreditRateFromRKI(cpr);
			}
		};
		return creditRate;
	}

	@Override
	public double getInterestRate() {

		Thread bankRateThread = new Thread() {

			@Override
			public void run() {

				bankRate = getInterestRateFromBank();
			}
		};
		return bankRate;
	}

	private String getCreditRateFromRKI(String cpr) {

		return CreditRator.i().rate(cpr).toString();
	}

	private double getInterestRateFromBank() {

		return InterestRate.i().todaysRate();
	}
}
