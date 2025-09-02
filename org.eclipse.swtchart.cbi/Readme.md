# Develop

1. Install Eclipse for RCP/RAP Developers via the [Eclipse installer](https://www.eclipse.org/downloads/)
2. Install RCPTT (Menu -> Help -> Eclipse Marketplace)
3. Clone/Import [SWTChart repository](https://github.com/eclipse/swtchart) -> branch "develop" 
4. Run Application (Create a new Eclipse Application "SWTChart Examples" and select "all workspace and enabled target plug-ins")

# Build

``` mvn -f org.eclipse.swtchart.cbi/pom.xml clean install ```


# Test

1. Download Eclipse for RCP and RAP Developers
2. org.eclipse.swtchart.extensions.demos.updatesite/site.xml -> Build All
3. Copy features and plugins folder -> dropins folder of "Eclipse for RCP and RAP Developers"
4. Run [RCPTT](https://eclipse.dev/rcptt/) tests
