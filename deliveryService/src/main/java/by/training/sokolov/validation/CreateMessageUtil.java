package by.training.sokolov.validation;

import java.util.List;

public final class CreateMessageUtil {

    public static StringBuilder createMessage(List<BrokenField> brokenFields) {

        StringBuilder message = new StringBuilder();
        message.append("Invalid input in next field(s): ");

        for (int i = 0; i < brokenFields.size(); i++) {

            if (i == brokenFields.size() - 1) {
                message.append(brokenFields.get(i).getFieldName());
                break;
            }
            message.append(brokenFields.get(i).getFieldName());
            message.append(", ");
        }

        return message;
    }
}
