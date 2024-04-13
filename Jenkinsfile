def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.1.7-TESTCYCLE-2020-DRY-RUN",
                               "Mule-runtime/mule-artifact-declaration/1.1.3-TESTCYCLE-2020-DRY-RUN" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
