package com.fast.cloud.core.model.base.po;

import java.io.File;
import java.util.Map;

public class JstreeNode implements Comparable<JstreeNode> {

    public JstreeNode(File file, String parent, String rootName) {
        this.id = parent + "/" + file.getName();
        this.text = file.getName();
        this.updateTime=file.lastModified();
        this.length=file.length();
        this.canRead=file.canRead();
        this.canWrite=file.canWrite();
        this.children = file.isDirectory() ? true : false;
        this.isDirectory = file.isDirectory() ? true : false;
        this.icon = file.isDirectory() ?
            "jstree-icon jstree-themeicon fa fa-folder icon-state-warning icon-lg jstree-themeicon-custom" :
            "jstree-icon jstree-themeicon fa fa-file icon-state-warning icon-lg jstree-themeicon-custom";
    }

    private String id;
    private boolean children;
    private boolean isDirectory;
    private long updateTime;
    private long length;
    private boolean canRead;
    private boolean canWrite;
    private boolean isHidden;
    private String text;
    private String icon;
    private JstreeState jstreeState;
    private Map<String, Object> li_attr;
    private Map<String, Object> a_attr;

    public long getUpdateTime() {
		return updateTime;
	}

	public long getLength() {
		return length;
	}

	public boolean isCanRead() {
		return canRead;
	}

	public boolean isCanWrite() {
		return canWrite;
	}

	public boolean isHidden() {
		return isHidden;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public JstreeState getJstreeState() {
        return jstreeState;
    }

    public void setJstreeState(JstreeState jstreeState) {
        this.jstreeState = jstreeState;
    }

    public Map<String, Object> getLi_attr() {
        return li_attr;
    }

    public void setLi_attr(Map<String, Object> li_attr) {
        this.li_attr = li_attr;
    }

    public Map<String, Object> getA_attr() {
        return a_attr;
    }

    public void setA_attr(Map<String, Object> a_attr) {
        this.a_attr = a_attr;
    }

    public Boolean isChildren() {
        return children;
    }

    public void setChildren(boolean children) {
        this.children = children;
    }

    public Boolean isDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

	@Override
	public int compareTo(JstreeNode jstreeNode) {
		return jstreeNode.isDirectory().compareTo(this.isDirectory);
	}
}