package core.objects;

public sealed interface LabelEditResult permits LabelEditResult.Success, LabelEditResult.Error {

    public final class Success implements LabelEditResult {}
    public record Error(String title, String message) implements LabelEditResult {}

}
