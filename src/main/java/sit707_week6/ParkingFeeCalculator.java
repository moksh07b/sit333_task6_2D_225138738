package sit707_week6;

public class ParkingFeeCalculator {

    public static final double LOST_TICKET_PENALTY = 50.0;
    public static final double DAILY_CAP = 40.0;

    /*
     * I added the correct multi-day daily cap calculation
     * and the lost ticket penalty is added after daily cap
     */
    public static double calculateFee(double hours, String vehicleType, boolean isPeak, boolean lostTicket) {

        if (hours < 0) {
            throw new IllegalArgumentException("Parking hours cannot be negative");
        }

        if (vehicleType == null) {
            throw new IllegalArgumentException("Vehicle type cannot be null");
        }

        hours = Math.ceil(hours);

        double hourlyRate;

        if (vehicleType.equalsIgnoreCase("STANDARD")) {
            hourlyRate = 5.0;
        } else if (vehicleType.equalsIgnoreCase("SUV")) {
            hourlyRate = 7.0;
        } else if (vehicleType.equalsIgnoreCase("MOTORBIKE")) {
            hourlyRate = 3.0;
        } else {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        double parkingFee = calculateParkingFeeBeforePenalty(hours, hourlyRate, isPeak);

        if (lostTicket) {
            parkingFee = parkingFee + LOST_TICKET_PENALTY;
        }

        return parkingFee;
    }

    private static double calculateParkingFeeBeforePenalty(double hours, double hourlyRate, boolean isPeak) {

        if (hours <= 2) {
            return 0.0;
        }

        double remainingHours = hours - 2;

        int fullDays = (int) (remainingHours / 24);
        double extraHours = remainingHours % 24;

        double fee = fullDays * DAILY_CAP;

        double extraFee = extraHours * hourlyRate;

        if (isPeak) {
            extraFee = extraFee * 1.20;
        }

        if (extraFee > DAILY_CAP) {
            extraFee = DAILY_CAP;
        }

        fee = fee + extraFee;

        return fee;
    }
}