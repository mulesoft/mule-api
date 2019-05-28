def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.2.1",
                               "Mule-runtime/mule-artifact-declaration/1.2.1" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings" ]

runtimeProjectsBuild(pipelineParams)
