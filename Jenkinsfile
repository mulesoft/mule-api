def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.1.x",
                               "Mule-runtime/mule-artifact-declaration/support/1.1.3" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings" ]

runtimeProjectsBuild(pipelineParams)
