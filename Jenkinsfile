def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/support/1.1.6",
                               "Mule-runtime/mule-artifact-declaration/support/1.1.2" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
