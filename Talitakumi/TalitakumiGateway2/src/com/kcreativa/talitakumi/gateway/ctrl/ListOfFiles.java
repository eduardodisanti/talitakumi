/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kcreativa.talitakumi.gateway.ctrl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eduardodisanti
 */
public class ListOfFiles {

    private String folder;

    public List getFileList() {

        List lst = new ArrayList();

        File myDir = new File(getFolder());
            if( myDir.exists() && myDir.isDirectory()){
                    File[] files = myDir.listFiles();
                    for(int i=0; i < files.length; i++){
                        lst.add(files[i]);
                    }
            }
        return(lst);
    }

    /**
     * @return the folder
     */
    public String getFolder() {
        return folder;
    }

    /**
     * @param folder the folder to set
     */
    public void setFolder(String folder) {
        this.folder = folder;
    }
}
