def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.2.2-TESTCYCLE-2020-DRY-RUN",
                               "Mule-runtime/mule-artifact-declaration/1.2.2-TESTCYCLE-2020-DRY-RUN" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       // Comment public setting to get oldMuleArtifact 4.2.1 from private repo till we move them to the public Repo
                       // Uncomment it after they are copied
                       //"mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
