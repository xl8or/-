package com.jgk.springrecipes.mvc.release.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="PROJECT_RELEASE")
public class Release extends AbstractReleaseEntity {

	@Id
	@Column(name="releaseId")
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;

	
	@ManyToOne
	@JoinColumn(name = "RELEASE_USER", insertable=false,updatable=false)
	private ReleaseUser user;
	private String releaseVersion;
	
	@ManyToOne
	@JoinColumn(name = "PROJECT")
	private Project project;
	public static Release createRelease(ReleaseUser _user,Project _project, String _releaseVersion) {
		Release release = new Release();
		release.user = _user;
		release.project=_project;
		release.releaseVersion=_releaseVersion;
		return release;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ReleaseUser getUser() {
		return user;
	}
	public void setUser(ReleaseUser user) {
		this.user = user;
	}
	public String getReleaseVersion() {
		return releaseVersion;
	}
	public void setReleaseVersion(String releaseVersion) {
		this.releaseVersion = releaseVersion;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	
	
}
