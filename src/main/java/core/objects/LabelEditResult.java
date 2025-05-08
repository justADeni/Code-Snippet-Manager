package core.objects;

public sealed interface LabelEditResult permits LabelEditResult.Success, LabelEditResult.Error {

    final class Success implements LabelEditResult {}
    record Error(String title, String message) implements LabelEditResult {}

}
