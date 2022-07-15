package dev.megashopper.common.dtos;

import dev.megashopper.common.utils.web.validators.ValidatorMessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @Email(message = ValidatorMessageUtil.EMAIL_REQUIREMENTS)
    @NotNull(message = ValidatorMessageUtil.EMAIL_REQUIRED)
    private String email;
}
