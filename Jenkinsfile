def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/1.0.x",
                               "Mule-runtime/mule-artifact-declaration/1.0.x" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                       "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings" ]

runtimeProjectsBuild(pipelineParams)
