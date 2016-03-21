
#include <game_Controller.h>
#include <windows.h>
#include <Xinput.h>
#include <jni.h>
#include <stdio.h>


#define X_INPUT_GET_STATE(name) DWORD WINAPI name(DWORD dwUserIndex, XINPUT_STATE *pState)
typedef X_INPUT_GET_STATE(x_input_get_state);
X_INPUT_GET_STATE(XInputGetStateStub)
{
	return(ERROR_DEVICE_NOT_CONNECTED);
}
static x_input_get_state *XInputGetState_ = XInputGetStateStub;
#define XInputGetState XInputGetState_



static void
LoadXInput(void);



extern "C"
JNIEXPORT jintArray JNICALL Java_game_Controller_getControllerState
(JNIEnv * env, jobject jobj)
{
	jintArray newArray = env->NewIntArray(4);
	jint* currentState = env->GetIntArrayElements(newArray, NULL);
	currentState[0] = 0;
	currentState[1] = 0;
	for (DWORD controllerIndex = 0; controllerIndex < XUSER_MAX_COUNT; ++controllerIndex)
	{

		XINPUT_STATE ControllerState;
		if (XInputGetState(controllerIndex, &ControllerState) == ERROR_SUCCESS)//controller found sucessfully
		{
			XINPUT_GAMEPAD* Pad = &ControllerState.Gamepad;

			currentState[0] = Pad->wButtons;
			currentState[1] = Pad->sThumbLX;
			currentState[2] = Pad->sThumbLY;

		}
	}



	env->ReleaseIntArrayElements(newArray, currentState, NULL);

	return newArray;
}

extern "C"
JNIEXPORT void JNICALL Java_game_Controller_loadXinput
(JNIEnv * env, jobject jobj)
{
	LoadXInput();
}

static void
LoadXInput(void)
{
	HMODULE XInputLibrary = LoadLibrary("xinput1_4.dll");
	
	if (!XInputLibrary)
	{
		XInputLibrary = LoadLibrary("xinput1_3.dll");
	}
	if (XInputLibrary)
	{
		XInputGetState = (x_input_get_state *)GetProcAddress(XInputLibrary, "XInputGetState");
	}
	
}



