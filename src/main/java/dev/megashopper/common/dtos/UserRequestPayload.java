package dev.megashopper.common.dtos;

import dev.megashopper.common.entities.Password;
import dev.megashopper.common.entities.User;
import dev.megashopper.common.utils.Generation;
import dev.megashopper.common.utils.web.validators.ValidatorMessageUtil;
import dev.megashopper.common.utils.web.validators.groups.OnCreate;
import dev.megashopper.common.utils.web.validators.groups.OnUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestPayload {

    @Null(
            message = ValidatorMessageUtil.PROVIDE_NO_ID_ON_CREATE,
            groups = OnCreate.class)
    @NotNull(
            message = ValidatorMessageUtil.ID_REQUIRED_ON_UPDATE,
            groups = OnUpdate.class)
    private String customerId;

    @Length(
            message = ValidatorMessageUtil.FNAME_REQUIREMENTS,
            min = 1,
            groups = {
                    OnCreate.class,
                    OnUpdate.class
            })
    @NotNull(
            message = ValidatorMessageUtil.FNAME_REQUIRED_ON_CREATE,
            groups = OnCreate.class)
    private String firstName;

    @Length(
            message = ValidatorMessageUtil.LNAME_REQUIREMENTS,
            min = 1,
            groups = {
                    OnCreate.class,
                    OnUpdate.class
            })
    @NotNull(
            message = ValidatorMessageUtil.LNAME_REQUIRED_ON_CREATE,
            groups = OnCreate.class)
    private String lastName;

    @Email(
            message = ValidatorMessageUtil.EMAIL_REQUIREMENTS,
            groups = {
                    OnCreate.class,
                    OnUpdate.class
            })
    @NotNull(
            message = ValidatorMessageUtil.EMAIL_REQUIRED_ON_CREATE,
            groups = OnCreate.class)
    private String email;

    @Length(
            message = ValidatorMessageUtil.USERNAME_REQUIREMENTS,
            min = 3,
            groups = {
                    OnCreate.class,
                    OnUpdate.class
            })
    @NotNull(
            message = ValidatorMessageUtil.USERNAME_REQUIRED_ON_CREATE,
            groups = OnCreate.class)
    private String username;

    @NotNull(
            message = ValidatorMessageUtil.PASSWORD_REQUIRED_ON_CREATE,
            groups = OnCreate.class)
    private String password;
    private String address;
    public User extractResource() {

        if (customerId == null) {
            return new User(firstName, lastName, email, username);
        }

        return new User(customerId, firstName, lastName, email, username, Generation.generatePassword(password));
    }
}
