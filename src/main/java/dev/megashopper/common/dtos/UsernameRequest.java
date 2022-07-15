package dev.megashopper.common.dtos;

import dev.megashopper.common.utils.web.validators.ValidatorMessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameRequest {

    @Length(message = ValidatorMessageUtil.USERNAME_REQUIREMENTS, min = 3)
    @NotNull(message = ValidatorMessageUtil.USERNAME_REQUIRED)
    private String username;
}
