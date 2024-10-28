import Enums.Status;

public class Epic extends Task {

    public Epic(String name, String description, Status status) {
        super(name, description, status);
    }
    @Override
    public String toString() {
        return "Epic{" +
                "name='" + super.getName() +
                ", description='" + super.getDescription() +
                ", status=" + super.getStatus() +
                '}';
    }
}
