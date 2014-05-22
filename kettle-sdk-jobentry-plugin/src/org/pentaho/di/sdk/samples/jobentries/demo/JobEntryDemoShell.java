package org.pentaho.di.sdk.samples.jobentries.demo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
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
import org.pentaho.di.ui.core.PropsUI;
import org.pentaho.di.ui.core.gui.WindowProperty;
import org.eclipse.swt.graphics.Point;

public class JobEntryDemoShell extends Shell {
	
	private Button btnOk;
	private Button btnCancel;
	private Text wName;
	private CCombo wOutcome;
	private Label wlOutcome;
	private Label wlName;

    // flag saving the changed status of the job entry configuration object
	private boolean changed;

	// From Pentaho
	private Class<?> PKG;
	private JobEntryDemo meta;
	private PropsUI props;

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
	 * @param parent
	 * @param arg1
	 */
	public JobEntryDemoShell(final Shell parent, final int arg1, final JobEntryDemoDialog dialog) {
		super(parent, arg1);
		setMinimumSize(new Point(350, 200));
		this.PKG = JobEntryDemoDialog.getPKG();
		this.meta = dialog.getMeta();
		this.props = dialog.getPropsUI();

        createContents();
		FormLayout formLayout = new FormLayout();
		formLayout.marginLeft = 10;
		formLayout.marginRight = 10;
		formLayout.marginTop = 10;
		formLayout.marginBottom = 10;
		setLayout(formLayout);

		btnOk = new Button(this, SWT.NONE);
		btnOk.addMouseListener(lsOk(parent, PKG));
		FormData fd_btnOk = new FormData();
		fd_btnOk.left = new FormAttachment(25);
		fd_btnOk.bottom = new FormAttachment(100);
		btnOk.setLayoutData(fd_btnOk);
		btnOk.setText(BaseMessages.getString(PKG, "System.Button.OK"));
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.addMouseListener(lsCancel(changed));
		FormData fd_btnCancel = new FormData();
		fd_btnCancel.right = new FormAttachment(75);
		fd_btnCancel.bottom = new FormAttachment(100);
		btnCancel.setLayoutData(fd_btnCancel);
		btnCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));
		
		int mdl = Const.MIDDLE_PCT;
        int margin = Const.MARGIN;
		
        // Job entry name line
        wlName = new Label(this, SWT.RIGHT);
        wlName.setText(BaseMessages.getString(PKG, "Demo.JobEntryName.Label"));
        FormData fd_wlName = new FormData();
        fd_wlName.left = new FormAttachment(0, 0);
		fd_wlName.right = new FormAttachment(mdl, 0);
        fd_wlName.top = new FormAttachment(0, margin);
        wlName.setLayoutData(fd_wlName);
        
        wName = new Text(this, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wName.addSelectionListener(new WNameSelectionListener(this));
        FormData fdName = new FormData();
        fdName.left = new FormAttachment(wlName, margin);
        fdName.top = new FormAttachment(0, margin);
        fdName.right = new FormAttachment(100, 0);
        wName.setLayoutData(fdName);
		
        // Outcome
        wlOutcome = new Label(this, SWT.RIGHT);
        wlOutcome.setText(BaseMessages.getString(PKG, "Demo.Outcome.Label"));
        FormData fd_wlOutcome = new FormData();
        fd_wlOutcome.left = new FormAttachment(0, 0);
        fd_wlOutcome.right = new FormAttachment(mdl, 0);
        fd_wlOutcome.top = new FormAttachment(wName, margin);
        wlOutcome.setLayoutData(fd_wlOutcome);
        wOutcome = new CCombo(this, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
        wOutcome.setItems(new String[]{"Positive","Negative"});
        wOutcome.setEditable(false);
        wOutcome.select(0);
        FormData fdOutcome = new FormData();
        fdOutcome.left = new FormAttachment(wlOutcome, margin);
        fdOutcome.top = new FormAttachment(wName, margin);
        fdOutcome.right = new FormAttachment(100, 0);
        wOutcome.setLayoutData(fdOutcome);

        props.setLook(wName);
		props.setLook(wOutcome);
		props.setLook(wlName);
		props.setLook(wlOutcome);
        props.setLook(this);

        // save the job entry's changed flag
        changed = meta.hasChanged();
        
        // The ModifyListener used on all controls. It will update the meta object to 
		// indicate that changes are being made.
        ModifyListener lsMod = new ModifyListener(){
            public void modifyText(ModifyEvent e){
                meta.setChanged();
            }
        };
        
        wName.addModifyListener(lsMod);
        wOutcome.addModifyListener(lsMod);
        
        // restore the changed flag to original value, as the modify listeners fire during dialog population 
        meta.setChanged(changed);
        
        populateDialog();
	}

    /**
     * Copy information from the meta-data input to the dialog fields.
     */
    private void populateDialog(){

    	// setting the name of the job entry
    	if (meta.getName() != null){
            wName.setText(meta.getName());
        }
        wName.selectAll();
        
        // choosing the configured value for the outcome on the selector box
        wOutcome.select(meta.getOutcome()?0:1);
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
		        cleanUp();
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
				ok(parent);
			}
		};
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText(BaseMessages.getString(PKG, "Demo.Shell.Title"));
		setSize(350, 200);
		Rectangle screenSize =  this.getDisplay().getPrimaryMonitor().getBounds();
		this.setLocation((screenSize.width - this.getBounds().width) / 2, (screenSize.height - this.getBounds().height) / 2);
	}

    /**
     * This helper method is called once the dialog is closed. It saves the placement of
     * the dialog, so it can be restored when it is opened another time.
     */
    private void cleanUp(){
    	// save dialog window placement to use when reopened 
        WindowProperty winprop = new WindowProperty(this);
        
        props.setScreen(winprop);
        // close dialog window
        this.dispose();
    }

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void ok(final Shell parent) {
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
    	cleanUp();

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
	    	ok(parent);
		}
	}
	
	/**
	 * @return
	 */
	public Button getBtnOk() {
		return btnOk;
	}
	
	/**
	 * @return
	 */
	public Button getBtnCancel() {
		return btnCancel;
	}
	
	/**
	 * @return
	 */
	public CCombo getWOutcome() {
		return wOutcome;
	}
	
	/**
	 * @return
	 */
	public Text getWName() {
		return wName;
	}
	
	/**
	 * @return
	 */
	public Label getWlOutcome() {
		return wlOutcome;
	}
	
	/**
	 * @return
	 */
	public Label getWlName() {
		return wlName;
	}
}
