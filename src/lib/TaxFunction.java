package lib;

public class TaxFunction {
	
	private static final int BASIC_NON_TAXABLE_INCOME = 54000000;
	private static final int MARRIED_ADDITION = 4500000;
	private static final int CHILD_ADDITION = 1500000;
	private static final double TAX_RATE = 0.05;
	
	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	
	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isMarried, int numberOfChildren) {
        int monthsWorked = validateNumberOfMonths(numberOfMonthWorking);
        int totalIncome = (monthlySalary + otherMonthlyIncome) * monthsWorked;
        int nonTaxableIncome = calculateNonTaxableIncome(isMarried, numberOfChildren);
        int taxableIncome = totalIncome - deductible - nonTaxableIncome;
        int tax = (int) Math.round(TAX_RATE * taxableIncome);
        return Math.max(tax, 0);
    }

    private static int validateNumberOfMonths(int numberOfMonths) {
        if (numberOfMonths > 12) {
            System.err.println("More than 12 month working per year");
            return 12;
        }
        return numberOfMonths;
    }

    private static int limitChildren(int numberOfChildren) {
        return Math.min(numberOfChildren, 3);
    }

    private static int calculateNonTaxableIncome(boolean isMarried, int numberOfChildren) {
        int nonTaxableIncome = BASIC_NON_TAXABLE_INCOME;
        if (isMarried) {
            nonTaxableIncome += MARRIED_ADDITION;
        }
        nonTaxableIncome += limitChildren(numberOfChildren) * CHILD_ADDITION;
        return nonTaxableIncome;
    }
}
