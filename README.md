How to develop Mi-Morphe PDI SDK plugins at Windows Eclipse environment

A. One time setup
----------------
1. Install Oracle JDK 7 from http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html.
2. Install Git for Window from http://msysgit.github.io/
2. Clone Pentaho PDI from https://github.com/cklee75/pentaho-kettle.git (In future will be from internal SVN)  
  `git clone https://github.com/cklee75/pentaho-kettle.git`
3. Swith to a tag to use a particular version instead of the latest, i.e.  
  `cd pentaho-kettle`  
  `git checkout 5.0.6b-R`
2. Install IDE either STS (http://spring.io/tools/sts/all) or Eclipse (https://www.eclipse.org/downloads/)
3. Install m2e from your Eclipse update site. (Skip if you are using STS as it has been built-in)
3. Install WindowBuilder Pro (SWT Designer) from your IDE/Eclipse update site (ref: http://www.vogella.com/tutorials/EclipseWindowBuilder/article.html#swtdesignerinstallation)
4. At Window -> Preference -> WindowBuilder -> SWT -> Code Generation -> Event handlers -> Event code generation: Create inner class.
5. At Window -> Preference -> WindowBuilder -> SWT -> Code Generation -> Varible generation -> check Field.
4. Install IvyDE at IDE/Eclipse from http://www.apache.org/dist/ant/ivyde/updatesite (ref: http://ant.apache.org/ivy/ivyde/download.cgi)
4. At Window -> Preference -> Ivy -> Settings, check reload the setting on demand, add Ivy setting path `ivysettings.xml`, Property files `build.properties`. (Note: Just the filename without path)
5. At Window -> Preference -> Ivy -> Classpath container, check Resolve dependencies in workspace.
4. Checkout Mi-Morphe from Internal SVN http://svn-ict.mimos.local/svn/mi-morphe/mi-morphe/trunk/06_Code_Directory/Pentaho/ (In future wll not be required after has deployed to internal artifactory server)

B. PDI UI project
---
1. Add **New Java project** for PDI UI, point the path to PDI UI project (i.e. `<pdi_root>\ui`).
2. Add Ivy managed dependencies library in new project wizard. Click Next (NOT Finish) -> Add Library -> IvyDE Managed Dependency (May take a while for a new build). You should only see JRE System Library and Ivy as library folders.
4. Right click project -> Build Path -> Link Source -> add location `<pdi_root>\assembly\package-res` with folder name package-res.
4. Run the UI (i.e. org.pentaho.di.ui.spoon.Spoon) and ensure there is no error. 
3. Do the same if want to put other PDI modules (e.g. Core, Engine) in debugging/run mode.

C. Mi-Morphe PDI Job
---
1. **Import maven project** for pdi-job.
2. At PDI UI Ant project, right click -> Build Path -> Link Source -> add the PDI Job path (until src\main\java folder), provide unique name.

D. For each PDI Job Entry
-----
1. **Add new maven project**.
2. Set `my.mimos.sdp2.morphe.pdi`, `pdi-plugin`, `pdi-job`, 1.0.0-SNAPSHOT as parent POM.
3. Set `my.mimos.sdp2.morphe.pdi.common`, `pdi-job`, `1.0.0-SNAPSHOT` as dependency.
3. Create concrete child classes extend from MorpheJobEntryBase, MorpheJobEntryDialog, MorpheShell.
4. At PDI UI Ant project, right click -> Build Path -> Link Source -> add the current PDI plugin path (until src\main\java folder), provide unique name.
5. At PDI UI Ant project, right click -> Build Path -> Link Source -> add the current PDI plugin path (until src\main\resources folder), provide unique name.
5. Add `-DKETTLE_PLUGIN_CLASSES=<full_qualified_Job_Entry_name>,<another_ull_qualified_Job_Entry_name>` (without bracket, e.g. `-DKETTLE_PLUGIN_CLASSES=my.mimos.sdp2.morphe.pdi.job.dq.duplicate.date.DuplicateDate`) as **VM arguments** at PDI UI Spoon.


References:
---
1. Official Help from Pentaho, HTML: http://infocenter.pentaho.com/help/nav/8_4, PDF: http://infocenter.pentaho.com/help/topic/pdi_embed_extend_guide/pdi_embed_extend_guide.pdf
2. E-Book http://library.books24x7.com  
  Pentaho Kettle Solutions: Building Open Source ETL Solutions with Pentaho Data Integration by  Matt Casters
3. E-Book http://site.ebrary.com/lib/mimos/home.action  
  Pentaho Data Integration 4 Cookbook by Sergio Pulvirenti, Adrián Carina Roldán, María  
  Pentaho 3.2 Data Integration: Beginner's Guide by Carina Roldan, Maria

Note:
---
2. Do not modify plugin-in ID. Refactoring (move package) on code is allowed.


