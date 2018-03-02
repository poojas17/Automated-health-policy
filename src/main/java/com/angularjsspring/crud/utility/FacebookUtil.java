package com.angularjsspring.crud.utility;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Component;

import com.angularjsspring.crud.domain.UserFacebookProfile;

@Component
public class FacebookUtil {
	
	@Autowired
	private Facebook facebook;
	
	@Inject
    public FacebookUtil(Facebook facebook) {
        this.facebook = facebook;
    }
	
	public UserFacebookProfile getFacebookUserProfile(String loginId) {
		
		User user=facebook.userOperations().getUserProfile();
		UserFacebookProfile userProfile = new UserFacebookProfile();
		userProfile.setFirstName(user.getFirstName());
		
		/*List<EducationExperience> educationExperienceList = user.getEducation();
		for(EducationExperience eduExp : educationExperienceList) {
			List<Experience> experienceList = eduExp.getClasses();
			for(Experience experience : experienceList) {
				experience.get
			}
		}*/
		userProfile.setEmail(user.getEmail());
		return userProfile;
	}
}
