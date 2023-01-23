def UPSTREAM_PROJECTS_LIST = [ "Mule-runtime/metadata-model-api/support/1.3.x",
                               "Mule-runtime/mule-artifact-declaration/1.3.0-FEBRUARY-2023" ]

Map pipelineParams = [ "upstreamProjects" : UPSTREAM_PROJECTS_LIST.join(','),
                      // Comment public setting to get oldMuleArtifact 4.3.0 from private repo till we move them to the public Repo
                      // Uncomment it after they are copied
                      // "mavenSettingsXmlId" : "mule-runtime-maven-settings-MuleSettings",
                      "projectType" : "Runtime" ]

runtimeBuild(pipelineParams)
