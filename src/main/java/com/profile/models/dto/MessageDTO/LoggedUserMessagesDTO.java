package com.profile.models.dto.MessageDTO;

import com.profile.models.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoggedUserMessagesDTO extends BaseEntity {


    private String sender;

    private String text;

    private String sentAt;

}
