def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/support/1.2.1",
                               "Mule-runtime/mule-artifact-declaration/1.2.1-DECEMBER-2021" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
