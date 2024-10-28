import Enums.Status;

public class Subtask extends Task {
    private Integer parentId;

    public Subtask(Integer parentId, String name, String description, Status status) {
        super(name, description, status);
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "parentId=" + parentId +
                ", name='" + super.getName() +
                ", description='" + super.getDescription() +
                ", status=" + super.getStatus() +
                '}';
    }
}
