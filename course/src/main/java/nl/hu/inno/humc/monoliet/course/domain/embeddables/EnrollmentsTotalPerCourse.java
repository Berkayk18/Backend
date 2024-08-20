package nl.hu.inno.humc.monoliet.course.domain.embeddables;

import nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions;

import java.util.Objects;

public class EnrollmentsTotalPerCourse {
    private int totalEnrollments;
    public EnrollmentsTotalPerCourse(int enrollmentsCount)
    {
       this.setTotalEnrollments(enrollmentsCount);
    }

    protected EnrollmentsTotalPerCourse(){}

    public int getTotalEnrollments() {
        return totalEnrollments;
    }

    private void setTotalEnrollments(int totalEnrollments){
        this.totalEnrollments = Math.max(totalEnrollments, 0);
    }

    public void updateTotalEnrollments(InstanceActions action) {
        switch (action) {
            case INCREMENT:
                this.totalEnrollments++;
                break;
            case DECREMENT:
                this.totalEnrollments--;
                break;
            default:
                throw new IllegalArgumentException("Something went wrong with calculation announcement count. Please connect support line");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentsTotalPerCourse that = (EnrollmentsTotalPerCourse) o;
        return totalEnrollments == that.totalEnrollments;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalEnrollments);
    }
}
