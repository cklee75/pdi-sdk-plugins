/*******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2012 by Pentaho : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.sdk.samples.jobentries.demo;

import java.util.Arrays;
import java.util.List;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.job.JobHopMeta;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.job.entries.columnsexist.JobEntryColumnsExist;
import org.pentaho.di.job.entry.JobEntryDialogInterface;
import org.pentaho.di.job.entry.JobEntryInterface;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.job.dialog.JobDialog;
import org.pentaho.di.ui.job.entry.JobEntryDialog;


/**
 * This class is part of the demo job entry plug-in implementation.
 * It demonstrates the basics of developing a plug-in job entry for PDI. 
 * 
 * The demo job entry is configurable to yield a positive or negative 
 * result. The job logic will follow the respective path during execution. 
 * 
 * This class is the implementation of JobEntryDialogInterface.
 * Classes implementing this interface need to:
 * 
 * - build and open a SWT dialog displaying the job entry's settings (stored in the entry's meta object)
 * - write back any changes the user makes to the job entry's meta object
 * - report whether the user changed any settings when confirming the dialog 
 * 
 */

public class JobEntryDemoDialog extends JobEntryDialog implements JobEntryDialogInterface{
	
	/**
	 *	The PKG member is used when looking up internationalized strings.
	 *	The properties file with localized keys is expected to reside in 
	 *	{the package of the class specified}/messages/messages_{locale}.properties   
	 */		
	private static Class<?> PKG = JobEntryDemo.class; // for i18n purposes

    // the job entry configuration object
    private JobEntryDemo meta;
	
    /**
	 * @return the pKG
	 */
	public static Class<?> getPKG() {
		return PKG;
	}

	/**
	 * @return the meta
	 */
	public JobEntryDemo getMeta() {
		return meta;
	}

	/**
     * The constructor should call super() and make sure that the name of the job entry is set.  
     * 
     * @param parent		the SWT Shell to use
     * @param jobEntryInt	the job entry settings object to use for the dialog
     * @param rep			the repository currently connected to, if any
     * @param jobMeta		the description of the job the job entry belongs to
     */
    public JobEntryDemoDialog(Shell parent, JobEntryInterface jobEntryInt, Repository rep, JobMeta jobMeta){
        super(parent, jobEntryInt, rep, jobMeta);
        // it is safe to cast the JobEntryInterface object to the object handled by this dialog
        meta = (JobEntryDemo) jobEntryInt;
        // ensure there is a default name for new job entries
        if (this.meta.getName() == null){
            this.meta.setName(BaseMessages.getString(PKG, "Demo.Default.Name"));
        }

        // TEMP, example how to retrieve incoming/outgoing entries.
        List<JobHopMeta> jobHopMetas = jobMeta.getJobhops();
        for (JobHopMeta jobHopMeta : jobHopMetas) {
        	if (jobHopMeta.getToEntry() != null && 
        			jobHopMeta.getToEntry().getObjectId() != null && 
        			jobHopMeta.getToEntry().getObjectId().equals(jobEntryInt.getObjectId())) {
        		System.out.println("Incoming job entry: " + jobHopMeta.getFromEntry().getName());
        	}
        }
        
        for (JobHopMeta jobHopMeta : jobHopMetas) {
        	if (jobHopMeta.getFromEntry() != null && 
        			jobHopMeta.getFromEntry().getObjectId() != null && 
        			jobHopMeta.getFromEntry().getObjectId().equals(jobEntryInt.getObjectId())) {
        		System.out.println("Outgoing job entry: " + jobHopMeta.getToEntry().getName());
        		if (jobHopMeta.getToEntry().getEntry() instanceof JobEntryColumnsExist) {
        			JobEntryColumnsExist columnsExist = (JobEntryColumnsExist) jobHopMeta.getToEntry().getEntry();
        			System.out.println("Database: " + columnsExist.getDatabase());
        			System.out.println("Table: " + columnsExist.getTablename());
        			System.out.println("Column: " + Arrays.toString(columnsExist.arguments));
        		}
        	}
        }

    }
    
	/**
	 * This method is called by Spoon when the user opens the settings dialog of the job entry.
	 * It should open the dialog and return only once the dialog has been closed by the user.
	 * 
	 * If the user confirms the dialog, the meta object (passed in the constructor) must
	 * be updated to reflect the new job entry settings. The changed flag of the meta object must 
	 * reflect whether the job entry configuration was changed by the dialog.
	 * 
	 * If the user cancels the dialog, the meta object must not be updated, and its changed flag
	 * must remain unaltered.
	 * 
	 * The open() method must return the met object of the job entry after the user has confirmed the dialog,
	 * or null if the user cancelled the dialog.
	 */
    public JobEntryInterface open(){
    	// SWT code for setting up the dialog
        Shell parent = getParent();
        Display display = parent.getDisplay();

		JobEntryDemoShell jobEntryDemoShell = new JobEntryDemoShell(parent,
				props.getJobsDialogStyle(), this);
        shell = jobEntryDemoShell;
        JobDialog.setShellImage(shell, meta);
        
		// open dialog and enter event loop 
        shell.open();
        while (!shell.isDisposed()){
            if (!display.readAndDispatch()){
            	display.sleep();
            }
        }
        
		// at this point the dialog has closed, so either ok() or cancel() have been executed
        return meta;
    }    
    
    /**
     * Expose protected props field to be used by SWT shell.
     * 
     * @return
     */
    public PropsUI getPropsUI() {
    	return this.props;
    }
    
}
