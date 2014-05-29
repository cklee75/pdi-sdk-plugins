How to develop Mi-Morphe PDI SDK plugins at Windows Eclipse environment

A. One time setup
----------------
1. Install Git for Window from http://msysgit.github.io/
2. Clone Pentaho PDI from https://github.com/cklee75/pentaho-kettle (In future will be from internal SVN)  
  `git clone https://github.com/cklee75/pentaho-kettle`
3. Swith to a tag to use a particular version instead of the latest, e.g.  
  `git checkout 5.0.6-R`
4. Checkout Mi-Morphe from Internal SVN http://svn-ict.mimos.local/svn/mi-morphe/mi-morphe/trunk/06_Code_Directory/Pentaho/ (Will not require after has deployed to internal artifactory server)
3. Install IvyDE at Eclipse from http://www.apache.org/dist/ant/ivyde/updatesite (ref: http://ant.apache.org/ivy/ivyde/download.cgi)
4. At Window -> Preference -> Ivy -> Settings, check reload the setting on demand, add Ivy setting path `ivysettings.xml`, Property files `build.properties`.
5. At Window -> Preference -> Ivy -> Classpath container, check Resolve dependencies in workspace.

B. PDI UI project
---
1. Add New Java project for PDI UI, point the path to PDI UI project.
2. Add Ivy managed dependencies library in new project wizard.
3. Make do the same if want to put other PDI modules (e.g. Core, Engine) in debugging mode.


C. For each PDI Job Entry
-----
1. Create maven project
2. Set `my.mimos.sdp2.morphe.pdi`, `pdi-plugin`, `pdi-job`, 1.0.0-SNAPSHOT as parent POM.
3. Set `my.mimos.sdp2.morphe.pdi.common`, `pdi-job`, `1.0.0-SNAPSHOT` as dependency.
3. Create concrete child classes extend from MorpheJobEntryBase, MorpheJobEntryDialog, MorpheShell.
4. At PDI UI Ant project, right click -> Build Path -> Link Source -> add the PDI plugin path, provide unique name.



