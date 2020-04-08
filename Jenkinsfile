def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/support/1.3.x",
                               "Mule-runtime/mule-artifact-declaration/support/1.3.0" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
