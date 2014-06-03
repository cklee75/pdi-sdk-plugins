How to develop Mi-Morphe PDI SDK plugins at Windows Eclipse environment

A. One time setup
----------------
1. Install Oracle JDK 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html.
2. Install Git for Window from http://msysgit.github.io/
2. Clone Pentaho PDI from https://github.com/cklee75/pentaho-kettle (In future will be from internal SVN)  
  `git clone https://github.com/cklee75/pentaho-kettle`
3. Swith to a tag to use a particular version instead of the latest, e.g.  
  `cd pentaho-kettle`  
  `git checkout 5.0.6-R`
4. Checkout Mi-Morphe from Internal SVN http://svn-ict.mimos.local/svn/mi-morphe/mi-morphe/trunk/06_Code_Directory/Pentaho/ (Will not require after has deployed to internal artifactory server)
3. Install WindowBuilder Pro (SWT Designer) from your Eclipse update site (ref: http://www.vogella.com/tutorials/EclipseWindowBuilder/article.html#swtdesignerinstallation)
4. At Window -> Preference -> WindowBuilder -> SWT -> Code Generation -> Event handlers -> Event code generation: Create inner class.
4. Install IvyDE at Eclipse from http://www.apache.org/dist/ant/ivyde/updatesite (ref: http://ant.apache.org/ivy/ivyde/download.cgi)
4. At Window -> Preference -> Ivy -> Settings, check reload the setting on demand, add Ivy setting path `ivysettings.xml`, Property files `build.properties`. (Note: Just the filename without path)
5. At Window -> Preference -> Ivy -> Classpath container, check Resolve dependencies in workspace.

B. PDI UI project
---
1. Add New Java project for PDI UI, point the path to PDI UI project (i.e. `<pdi_root>\ui`).
2. Add Ivy managed dependencies library in new project wizard. (May take a while for a new build)
4. Right click project -> Build Path -> Link Source -> add location `<pdi_root>\assembly\package-res` with folder name package-res.
5. Add SWT of your platform to ivy.setting, e.g.  
`<dependency org="org.eclipse.swt"       name="swt-win32"    rev="3.3.0.3346" transitive="false" />`
4. Run the UI (i.e. Spoon) and ensure there is no error. 
3. Do the same if want to put other PDI modules (e.g. Core, Engine) in debugging mode.

C. Mi-Morphe PDI Job
---
1. Import maven project for pdi-job
2. At PDI UI Ant project, right click -> Build Path -> Link Source -> add the PDI Job path, provide unique name.

D. For each PDI Job Entry
-----
1. Add new maven project
2. Set `my.mimos.sdp2.morphe.pdi`, `pdi-plugin`, `pdi-job`, 1.0.0-SNAPSHOT as parent POM.
3. Set `my.mimos.sdp2.morphe.pdi.common`, `pdi-job`, `1.0.0-SNAPSHOT` as dependency.
3. Create concrete child classes extend from MorpheJobEntryBase, MorpheJobEntryDialog, MorpheShell.
4. At PDI UI Ant project, right click -> Build Path -> Link Source -> add the current PDI plugin path, provide unique name.
5. 


Note:
---
1. Official Help from Pentaho, HTML: http://infocenter.pentaho.com/help/nav/8_4, PDF: http://infocenter.pentaho.com/help/topic/pdi_embed_extend_guide/pdi_embed_extend_guide.pdf
2. Do not modify plugin-in ID. Refactoring (move package) on code is allowed.
2. 


