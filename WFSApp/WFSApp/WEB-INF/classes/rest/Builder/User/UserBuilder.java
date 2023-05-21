package rest.builder.user;

import rest.builder.Built;
import rest.model.user.in.IUserData;
import rest.model.user.out.IUserModel;
import jakarta.inject.Inject;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Default;

public class UserBuilder {
    
    @Inject @Default
    private IUserModel userModel;

    @Inject @Default
    private IUserData userRepos;

    @Produces @Built
    public IUserModel buildModel(){
        userModel.setDataSource(userRepos);
        return userModel;
    }

}
