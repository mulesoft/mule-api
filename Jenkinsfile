def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/support/1.2.0",
                               "Mule-runtime/mule-artifact-declaration/support/1.2.0" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
// MULE-18633: Use maven settings with private repos for mule-api support/1.2.0 to avoid failing trying to find mule-module-maven-plugin 1.1.0-20200518
//                        "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                       "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
