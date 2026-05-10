package sit707_week6;

import org.junit.Assert;
import org.junit.Test;

public class ABCD_test {
    
    // For Any Normal Vehicle 
    @Test
    public void testStandardVehicleFee() {
        double fee = ParkingFeeCalculator.calculateFee(3, "STANDARD", false, false);
        Assert.assertEquals(5.0, fee, 0.01);
    }

    // For any SUV
    @Test
    public void testSUVFee() {
        double fee = ParkingFeeCalculator.calculateFee(3, "SUV", false, false);
        Assert.assertEquals(7.0, fee, 0.01);
    }

    // For any Motorbike
    @Test
    public void testMotorbikeFee() {
        double fee = ParkingFeeCalculator.calculateFee(3, "MOTORBIKE", false, false);
        Assert.assertEquals(3.0, fee, 0.01);
    }

    // For any Negative Input
    @Test(expected = IllegalArgumentException.class)
    public void testNegativeFee() {
        ParkingFeeCalculator.calculateFee(-3, "MOTORBIKE", false, false);
    }

    // Peak pricing should be higher than normal pricing
    @Test
    public void testPeakPricing() {
        double normalFee = ParkingFeeCalculator.calculateFee(3, "STANDARD", false, false);
        double peakFee = ParkingFeeCalculator.calculateFee(3, "STANDARD", true, false);
        Assert.assertTrue(peakFee > normalFee);
    }

    // Lost ticket should add penalty after parking calculation
    @Test
    public void testLostTicketPenalty() {
        double fee = ParkingFeeCalculator.calculateFee(3, "STANDARD", false, true);
        Assert.assertEquals(55.0, fee, 0.01);
    }

    // Boundary testing: first 2 hours should be free
    @Test
    public void testFirstTwoHoursFree() {
        double fee = ParkingFeeCalculator.calculateFee(1.5, "STANDARD", false, false);
        Assert.assertEquals(0.0, fee, 0.01);
    }

    // Boundary testing: 2.1 hours should round up to 3 hours
    @Test
    public void testRoundingUpHours() {
        double fee = ParkingFeeCalculator.calculateFee(2.1, "STANDARD", false, false);
        Assert.assertEquals(5.0, fee, 0.01);
    }

    // Boundary testing: only 1 hour should be charged after free period
    @Test
    public void testThreeHoursParking() {
        double fee = ParkingFeeCalculator.calculateFee(3, "STANDARD", false, false);
        Assert.assertEquals(5.0, fee, 0.01);
    }

    // Daily cap should apply for one day
    @Test
    public void testDailyCap() {
        double fee = ParkingFeeCalculator.calculateFee(20, "SUV", false, false);
        Assert.assertEquals(40.0, fee, 0.01);
    }

    // Multi-day parking should apply daily cap for each full day
    @Test
    public void testTwoDayParkingCap() {
        double fee = ParkingFeeCalculator.calculateFee(42, "SUV", false, false);
        Assert.assertEquals(80.0, fee, 0.01);
    }
    
 // Multi-day parking should apply daily cap for each full day
    @Test
    public void testOneDayParkingCapAndExtraHours() {
        double fee = ParkingFeeCalculator.calculateFee(29, "SUV", false, false);	// First two hours are free and after that
        Assert.assertEquals(61.0, fee, 0.01);
    }

    // Lost ticket should not be included inside daily cap
    @Test
    public void testLostTicketAboveDailyCap() {
        double fee = ParkingFeeCalculator.calculateFee(20, "SUV", false, true);
        Assert.assertEquals(90.0, fee, 0.01);
    }

    // Invalid vehicle type should not be accepted
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidVehicleType() {
        ParkingFeeCalculator.calculateFee(3, "TRUCK", false, false);
    }

    // Null vehicle type should not be accepted
    @Test(expected = IllegalArgumentException.class)
    public void testNullVehicleType() {
        ParkingFeeCalculator.calculateFee(3, null, false, false);
    }
}