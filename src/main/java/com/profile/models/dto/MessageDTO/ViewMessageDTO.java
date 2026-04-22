package com.profile.models.dto.MessageDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ViewMessageDTO {
   private Long id;
   private String subject;
   private String message;
}
