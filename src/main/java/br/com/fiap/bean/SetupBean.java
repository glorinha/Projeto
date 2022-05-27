package br.com.fiap.bean;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.model.file.UploadedFile;

import br.com.fiap.dao.SetupDao;
import br.com.fiap.model.Setup;
import br.com.fiap.model.User;

@Named
@RequestScoped
public class SetupBean {

	private Setup setup = new Setup();
	private List<Setup> list;
	private UploadedFile image;
	
	public SetupBean() {
		list = this.list();
	}
	
	public String save() throws IOException{
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String path = servletContext.getRealPath("/"); 
		
		FileOutputStream out = new FileOutputStream(path + "\\images\\" + image.getFileName());
		out.write(image.getContent());
		out.close();
		
		setup.setImagePath("\\images\\" + image.getFileName());
		
		User user = (User) FacesContext
				.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.get("user");
		
		new SetupDao().create(setup);
		user.addSetup(setup);
		
		mostrarMensagem();
		
		return "setups?faces-redirect=true";
	}

	private void mostrarMensagem() {
		FacesContext
			.getCurrentInstance()
			.getExternalContext()
			.getFlash()
			.setKeepMessages(true);
		
		FacesContext
			.getCurrentInstance()
			.addMessage(null, new FacesMessage("Setup cadastrado com sucesso"));
	}
	
	public List<Setup> list(){
		return new SetupDao().listAll();
	}
	
	public String remove(Setup setup) {
		new SetupDao().delete(setup);
		
		FacesContext
			.getCurrentInstance()
			.addMessage(null, new FacesMessage("Setup apagado com sucesso"));
		
		return "setups?faces-redirect=true";
	}
	
	public List<Setup> getList() {
		return list;
	}

	public void setList(List<Setup> list) {
		this.list = list;
	}

	public Setup getSetup() {
		return setup;
	}

	public void setSetup(Setup setup) {
		this.setup = setup;
	}

	public UploadedFile getImage() {
		return image;
	}

	public void setImage(UploadedFile image) {
		this.image = image;
	}

}
