package org.pentaho.di.sdk.samples.jobentries.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class JobEntryDemoShell extends Shell {
	
	private Button btnOk;
	private Button btnCancel;
	private Class<?> PKG;
	private JobEntryDemo meta;
	private Text wName;
	private CCombo wOutcome;
	private int middle;
	private Label wlOutcome;
	private Label wlName;
	private boolean changed;

	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			JobEntryDemoShell shell = new JobEntryDemoShell(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the shell.
	 * @param display
	 */
	/*public JobEntryDemoShell(Display display) {
		super(display, SWT.SHELL_TRIM);
		createContents();
	}*/

	
	
	/**
	 * @param parent
	 * @param arg1
	 */
	public JobEntryDemoShell(final Shell parent, final int arg1, final Class<?> PKG,
			final JobEntryDemo jobEntryDemo, final int middle, final boolean changed) {
		super(parent, arg1);
		this.PKG = PKG;
		this.meta = jobEntryDemo;
		this.middle = middle;
		this.changed = changed;

        createContents();
		setLayout(new FormLayout());

		btnOk = new Button(this, SWT.NONE);
		btnOk.addMouseListener(lsOk(parent, PKG));
		FormData fd_btnOk = new FormData();
		fd_btnOk.bottom = new FormAttachment(100, -237);
		fd_btnOk.top = new FormAttachment(0);
		fd_btnOk.right = new FormAttachment(6);
		fd_btnOk.left = new FormAttachment(0);
		btnOk.setLayoutData(fd_btnOk);
//		btnOk.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0) {
//				
//			}
//		});
//		FormData fd_btnOk = new FormData();
//		fd_btnOk.right = new FormAttachment(0, 111);
//		fd_btnOk.top = new FormAttachment(0, 227);
//		fd_btnOk.left = new FormAttachment(0, 36);
//		btnOk.setLayoutData(fd_btnOk);
		btnOk.setText("OK");
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.addMouseListener(lsCancel(changed));
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.bottom = new FormAttachment(100, -237);
		fd_btnCancel.top = new FormAttachment(0);
		fd_btnCancel.right = new FormAttachment(100, -386);
		fd_btnCancel.left = new FormAttachment(0);
		btnCancel.setLayoutData(fd_btnCancel);
//		FormData fd_btnCancel = new FormData();
//		fd_btnCancel.right = new FormAttachment(0, 209);
//		fd_btnCancel.top = new FormAttachment(0, 227);
//		fd_btnCancel.left = new FormAttachment(0, 134);
//		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText("Cancel");
		
       //int middle = props.getMiddlePct();
		//int middle = 1;
        int margin = Const.MARGIN;
		
        // Job entry name line
        wlName = new Label(this, SWT.RIGHT);
        wlName.setText(BaseMessages.getString(PKG, "Demo.JobEntryName.Label"));
        //props.setLook(wlName);
        FormData fd_wlName = new FormData();
        fd_wlName.left = new FormAttachment(0, 0);
		fd_wlName.right = new FormAttachment(middle, 0);
        fd_wlName.top = new FormAttachment(0, margin);
        wlName.setLayoutData(fd_wlName);
        
        wName = new Text(this, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wName.addSelectionListener(new WNameSelectionListener(this));
//        props.setLook(wName);
//        wName.addModifyListener(lsMod);
        FormData fdName = new FormData();
        fdName.left = new FormAttachment(middle, 0);
        fdName.top = new FormAttachment(0, margin);
        fdName.right = new FormAttachment(100, 0);
        wName.setLayoutData(fdName);
		
        // Outcome
        wlOutcome = new Label(this, SWT.RIGHT);
        wlOutcome.setText(BaseMessages.getString(PKG, "Demo.Outcome.Label"));
//        props.setLook(wlOutcome);
        FormData fd_wlOutcome = new FormData();
        fd_wlOutcome.left = new FormAttachment(0, 0);
        fd_wlOutcome.right = new FormAttachment(middle, -margin);
        fd_wlOutcome.top = new FormAttachment(wName, margin);
        wlOutcome.setLayoutData(fd_wlOutcome);
        wOutcome = new CCombo(this, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
        wOutcome.setItems(new String[]{"Positive","Negative"});
        wOutcome.setEditable(false);
        wOutcome.select(0);
//        wOutcome.addModifyListener(lsMod);
//        props.setLook(wOutcome);
        FormData fdOutcome = new FormData();
        fdOutcome.left = new FormAttachment(middle, 0);
        fdOutcome.top = new FormAttachment(wName, margin);
        fdOutcome.right = new FormAttachment(100, 0);
        //fdOutcome.bottom = new FormAttachment(100, -40);
        wOutcome.setLayoutData(fdOutcome);

	}

	/**
	 * @param changed
	 * @return
	 */
	private MouseAdapter lsCancel(final boolean changed) {
		return new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
		    	// restore changed flag on the meta object, any changes done by the modify listener
		    	// are being revoked here
		        meta.setChanged(changed);
		        // this variable will be returned by the open() method, setting it to null, as open() needs
		        // to return null when the dialog is cancelled
		        meta = null;
		        // close dialog window and clean up
		        dispose();
			}
		};
	}

	/**
	 * @param parent
	 * @param PKG
	 * @return
	 */
	private MouseAdapter lsOk(final Shell parent, final Class<?> PKG) {
		return new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
		    	// make sure the job entry name is set properly, return with an error message if that is not the case
		    	if(Const.isEmpty(wName.getText())) {
		    		MessageBox mb = new MessageBox(parent, SWT.OK | SWT.ICON_ERROR );
		    		mb.setText(BaseMessages.getString(PKG, "System.StepJobEntryNameMissing.Title"));
		    		mb.setMessage(BaseMessages.getString(PKG, "System.JobEntryNameMissing.Msg"));
		    		mb.open();
		    		return;
		    	}
		    	
		    	// update the meta object with the entered dialog settings
		    	meta.setName(wName.getText());
		    	meta.setOutcome(wOutcome.getSelectionIndex()==0);

		    	// close dialog window and clean up
		        dispose();
			}
		};
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	public Button getBtnOk() {
		return btnOk;
	}
	public Button getBtnCancel() {
		return btnCancel;
	}
	public CCombo getWOutcome() {
		return wOutcome;
	}
	public Text getWName() {
		return wName;
	}
	public Label getWlOutcome() {
		return wlOutcome;
	}
	public Label getWlName() {
		return wlName;
	}

	private class WNameSelectionListener extends SelectionAdapter {
		
		private final Shell parent;
		
		/**
		 * @param parent
		 */
		public WNameSelectionListener(Shell parent) {
			super();
			this.parent = parent;
		}

		@Override
		public void widgetDefaultSelected(final SelectionEvent arg0) {
	    	// make sure the job entry name is set properly, return with an error message if that is not the case
	    	if(Const.isEmpty(wName.getText())) {
	    		MessageBox mb = new MessageBox(parent, SWT.OK | SWT.ICON_ERROR );
	    		mb.setText(BaseMessages.getString(PKG, "System.StepJobEntryNameMissing.Title"));
	    		mb.setMessage(BaseMessages.getString(PKG, "System.JobEntryNameMissing.Msg"));
	    		mb.open();
	    		return;
	    	}
	    	
	    	// update the meta object with the entered dialog settings
	    	meta.setName(wName.getText());
	    	meta.setOutcome(wOutcome.getSelectionIndex()==0);

	    	// close dialog window and clean up
	        dispose();
		}
	}

}
