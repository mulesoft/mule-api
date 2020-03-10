def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.3.x",
                               "Mule-runtime/mule-artifact-declaration/1.3.x" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
