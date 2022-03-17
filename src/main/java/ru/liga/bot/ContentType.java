package ru.liga.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.liga.Transate;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ContentType {
    private final SendMessage messageText;
    private final SendPhoto photo;
    private final boolean isGraph;

    public ContentType(boolean isGraph) {
        if (isGraph){
            photo = new SendPhoto();
            messageText = null;
        }else {
            messageText = new SendMessage();
            photo = null;
        }
        this.isGraph = isGraph;
    }

    private void setChatId(String chatId){
        if (messageText != null){
            messageText.setChatId(chatId);
        }else{
            photo.setChatId(chatId);
        }
    }

    public void setContent(List<Map<LocalDate, Double>> content, String chatId){
        if (!isGraph){
            Map<LocalDate, Double> value;
            StringBuilder str = new StringBuilder();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("E dd.MM.yyyy", Locale.getDefault());
            for (int i = 0; i < content.size(); i++){
                value = content.get(i);
                for (Map.Entry<LocalDate, Double> entry : value.entrySet()){
                    str.append(Transate.translatorToRussian(entry.getKey().format(format).substring(0, 3)))
                            .append(" ").append(entry.getKey().format(format).substring(3));
                    str.append(String.format(" - %.2f\n", entry.getValue()));
                }
                str.append("\n");
            }
            messageText.setText(str.toString());
        }else{
            photo.setPhoto(new InputFile(new File("src/main/resources/graf.png")));
        }
        setChatId(chatId);
    }

    public boolean isGraph() {
        return isGraph;
    }

    public SendMessage getMessageText() {
        return messageText;
    }

    public SendPhoto getPhoto() {
        return photo;
    }
}
