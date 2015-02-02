/**
 * Copyright 2007 DFKI GmbH.
 * All Rights Reserved.  Use is subject to license terms.
 *
 * This file is part of MARY TTS.
 *
 * MARY TTS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package marytts.server.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import marytts.features.FeatureProcessorManager;
import marytts.features.FeatureRegistry;
import marytts.modules.synthesis.Voice;
import marytts.util.MaryRuntimeUtils;
import marytts.util.MaryUtils;
import marytts.util.http.Address;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.nio.entity.NStringEntity;

/**
 * Processor class for information http requests to Mary server
 * 
 * @author Oytun T&uuml;rk, Marc Schr&ouml;der
 */
public class InfoRequestHandler extends BaseHttpRequestHandler {
	// CorrectMethod interface and HashMap methodsMapping serve the purpose of directing URL to correct function
	public interface CorrectMethod {
		String callCorrectMethod(Map<String, String> queryItems, HttpResponse response);
	}

	public Map<String, CorrectMethod> methodsMapping;

	public InfoRequestHandler() {
		super();
		methodsMapping = new HashMap<String, CorrectMethod>();

		methodsMapping.put("voices", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return MaryRuntimeUtils.getVoices(queryItems);
			}
		});

		methodsMapping.put("version", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return MaryRuntimeUtils.getMaryVersion();
			}
		});

		methodsMapping.put("datatypes", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return MaryRuntimeUtils.getDataTypes();
			}
		});

		methodsMapping.put("locales", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return MaryRuntimeUtils.getLocales();
			}
		});

		methodsMapping.put("audioformats", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return MaryRuntimeUtils.getAudioFileFormatTypes();
			}
		});

		methodsMapping.put("exampletext", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					// Voice example text
					String voice = queryItems.get("voice");
					if (voice != null) {
						return MaryRuntimeUtils.getVoiceExampleText(voice);
					}
					String datatype = queryItems.get("datatype");
					String locale = queryItems.get("locale");
					if (datatype != null && locale != null) {
						Locale loc = MaryUtils.string2locale(locale);
						return MaryRuntimeUtils.getExampleText(datatype, loc);
					}
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'datatype' and 'locale' or 'voice'");
				return null;
			}
		});

		methodsMapping.put("audioeffects", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return MaryRuntimeUtils.getDefaultAudioEffects();
			}
		});

		methodsMapping.put("audioeffect-default-param", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					String effect = queryItems.get("effect");
					if (effect != null)
						return MaryRuntimeUtils.getAudioEffectDefaultParam(effect);
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'effect'");
				return null;
			}
		});

		methodsMapping.put("audioeffect-full", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					String effect = queryItems.get("effect");
					String params = queryItems.get("params");
					if (effect != null && params != null) {
						return MaryRuntimeUtils.getFullAudioEffect(effect, params);
					}
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'effect' and 'params'");
				return null;
			}
		});

		methodsMapping.put("audioeffect-help", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					String effect = queryItems.get("effect");
					if (effect != null) {
						return MaryRuntimeUtils.getAudioEffectHelpText(effect);
					}
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'effect'");
				return null;
			}
		});

		methodsMapping.put("audioeffect-is-hmm-effect", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					String effect = queryItems.get("effect");
					if (effect != null) {
						return MaryRuntimeUtils.isHmmAudioEffect(effect);
					}
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'effect'");
				return null;
			}
		});

		methodsMapping.put("features", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return getRequestedFeature(false, queryItems, response);
			}
		});

		methodsMapping.put("features-discrete", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				return getRequestedFeature(true, queryItems, response);
			}
		});

		methodsMapping.put("vocalizations", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					String voice = queryItems.get("voice");
					if (voice != null) {
						return MaryRuntimeUtils.getVocalizations(voice);
					}
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'voice'");
				return null;
			}
		});

		methodsMapping.put("styles", new CorrectMethod() {
			@Override
			public String callCorrectMethod(Map<String, String> queryItems, HttpResponse response) {
				if (queryItems != null) {
					String voice = queryItems.get("voice");
					if (voice != null) {
						return MaryRuntimeUtils.getStyles(voice);
					}
				}
				MaryHttpServerUtils.errorMissingQueryParameter(response, "'voice'");
				return null;
			}
		});
	}

	protected String getRequestedFeature(boolean isFeatureDiscreteRequest, Map<String, String> queryItems, HttpResponse response) {
		if (queryItems != null) {
			// List of features that can be computed for the voice
			FeatureProcessorManager mgr = null;
			String voiceName = queryItems.get("voice");
			String localeName = queryItems.get("locale");
			if (voiceName != null) {
				Voice voice = Voice.getVoice(voiceName);
				if (voice == null) {
					MaryHttpServerUtils.errorWrongQueryParameterValue(response, "voice", voiceName, "No voice with that name");
					return null;
				}
				mgr = FeatureRegistry.getFeatureProcessorManager(voice);
				if (mgr == null) {
					mgr = FeatureRegistry.getFeatureProcessorManager(voice.getLocale());
				}
				if (mgr == null) {
					mgr = FeatureRegistry.getFeatureProcessorManager(new Locale(voice.getLocale().getLanguage()));
				}
				if (mgr == null) {
					mgr = FeatureRegistry.getFallbackFeatureProcessorManager();
				}
			} else if (localeName != null) {
				Locale locale = MaryUtils.string2locale(localeName);
				mgr = FeatureRegistry.getFeatureProcessorManager(locale);
				if (mgr == null) {
					mgr = FeatureRegistry.getFeatureProcessorManager(new Locale(locale.getLanguage()));
				}
				if (mgr == null) {
					StringBuilder localeList = new StringBuilder();
					for (Locale l : FeatureRegistry.getSupportedLocales()) {
						if (localeList.length() > 0)
							localeList.append(",");
						localeList.append(l.toString());
					}
					MaryHttpServerUtils.errorWrongQueryParameterValue(response, "locale", localeName,
							"The locale is not supported.<br />" + "Supported locales: <code>" + localeList + "</code>");
					return null;
				}
			}
			if (mgr != null)
				// if (request.equals("features-discrete")) {
				if (isFeatureDiscreteRequest) {
					String discreteFeatureNames = mgr.listByteValuedFeatureProcessorNames()
							+ mgr.listShortValuedFeatureProcessorNames();
					return discreteFeatureNames;
				}
			return mgr.listFeatureProcessorNames();
		}
		MaryHttpServerUtils.errorMissingQueryParameter(response, "'voice' or 'locale'");
		return null;
	}

	@Override
	protected void handleClientRequest(String absPath, Map<String, String> queryItems, HttpResponse response,
			Address serverAddressAtClient) throws IOException {
		// Individual info request
		String infoResponse = handleInfoRequest(absPath, queryItems, response);
		if (infoResponse == null) { // error condition, handleInfoRequest has set an error message
			return;
		}

		response.setStatusCode(HttpStatus.SC_OK);
		try {
			NStringEntity entity = new NStringEntity(infoResponse, "UTF-8");
			entity.setContentType("text/plain; charset=UTF-8");
			response.setEntity(entity);
		} catch (UnsupportedEncodingException e) {
		}
	}

	private String handleInfoRequest(String absPath, Map<String, String> queryItems, HttpResponse response) {
		logger.debug("New info request: " + absPath);
		if (queryItems != null) {
			for (String key : queryItems.keySet()) {
				logger.debug("    " + key + "=" + queryItems.get(key));
			}
		}

		assert absPath.startsWith("/") : "Absolute path '" + absPath + "' does not start with a slash!";
		String request = absPath.substring(1); // without the initial slash

		String finalOutput = methodsMapping.get(request).callCorrectMethod(queryItems, response);

		if (finalOutput == null) {
			MaryHttpServerUtils.errorFileNotFound(response, request);
			return null;
		} else {
			return finalOutput;
		}
	}
}