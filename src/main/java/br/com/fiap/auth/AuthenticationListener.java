package br.com.fiap.auth;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.fiap.model.User;

public class AuthenticationListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		String page = FacesContext
			.getCurrentInstance()
			.getViewRoot()
			.getViewId();
		
		
		if (page.equals("/login.xhtml") || page.equals("/register.xhtml")) return;
		
		
		// verificar se o user esta logado
		User user = (User) FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getSessionMap()
			.get("user");
		
		if(user != null) return;
		
		//caso contrario, encaminha para login
		FacesContext
			.getCurrentInstance()
			.getApplication()
			.getNavigationHandler()
			.handleNavigation(FacesContext.getCurrentInstance(), 
								null, "/login.xhtml");
		
		
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
