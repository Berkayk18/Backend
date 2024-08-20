package nl.hu.inno.humc.monoliet.course.domain.embeddables;

import nl.hu.inno.humc.monoliet.course.domain.enums.InstanceActions;

import java.util.Objects;

public class AnnouncementTotalPerCourse {

    private int totalAnnouncement;

    public AnnouncementTotalPerCourse(int announcementsCount)
    {
        this.setTotalAnnouncement(announcementsCount);
    }

    protected AnnouncementTotalPerCourse(){}

    public int getTotalAnnouncement() {
        return totalAnnouncement;
    }

    private void setTotalAnnouncement(int totalAnnouncement){
        this.totalAnnouncement = Math.max(totalAnnouncement, 0);
    }

    public void updateTotalAnnouncements(InstanceActions action) {
        switch (action) {
            case INCREMENT:
                this.totalAnnouncement++;
                break;
            case DECREMENT:
                this.totalAnnouncement--;
                break;
            default:
                throw new IllegalArgumentException("Something went wrong with calculation announcement count. Please connect supportline");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnouncementTotalPerCourse that = (AnnouncementTotalPerCourse) o;
        return totalAnnouncement == that.totalAnnouncement;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalAnnouncement);
    }
}
