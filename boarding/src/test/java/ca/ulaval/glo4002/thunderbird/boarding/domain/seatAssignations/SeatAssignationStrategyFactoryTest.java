package ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations;

import ca.ulaval.glo4002.thunderbird.boarding.domain.seatAssignations.SeatAssignationStrategyFactory.AssignMode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class SeatAssignationStrategyFactoryTest {
    private final SeatAssignationStrategyFactory factory = new SeatAssignationStrategyFactory();

    @Test
    public void givenModeRandom_whenGettingStrategy_shouldReturnRandomSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(AssignMode.RANDOM);

        assertThat(strategy, instanceOf(RandomSeatAssignationStrategy.class));
    }

    @Test
    public void givenModeCheapest_whenGettingStrategy_shouldReturnCheapestSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(AssignMode.CHEAPEST);

        assertThat(strategy, instanceOf(CheapestSeatAssignationStrategy.class));
    }

    @Test
    public void givenModeMostLegRoom_whenGettingStrategy_shouldReturnMostLegRoomSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(AssignMode.LEGS);

        assertThat(strategy, instanceOf(MostLegRoomSeatAssignationStrategy.class));
    }

    @Test
    public void givenModeBestView_whenGettingStrategy_shouldReturnMostLegRoomSeatStrategy() {
        SeatAssignationStrategy strategy = factory.getStrategy(AssignMode.LANDSCAPE);

        assertThat(strategy, instanceOf(LandscapeSeatAssignationStrategy.class));
    }
}