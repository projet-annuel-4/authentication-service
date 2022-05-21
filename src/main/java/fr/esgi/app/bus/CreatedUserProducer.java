package fr.esgi.app.bus;

import fr.esgi.app.domain.User;
import fr.esgi.app.dto.user.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component("createdUserProducer")
public class CreatedUserProducer {
    static final String USER_CREATED_OUTPUT = "createdUserProducer-out-0";
    private final StreamBridge streamBridge;

    public void userCreated(User user) {
        var userEvent = UserEvent.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .city(user.getCity())
                .phoneNumber(user.getPhoneNumber())
                .postIndex(user.getPostIndex())
                .imgUrl(user.getImgUrl())
                .build();

        streamBridge.send(USER_CREATED_OUTPUT, userEvent);

        log.info("News user with id '{}' and email '{}' sent to bus.", userEvent.getId(), userEvent.getEmail());
    }
}