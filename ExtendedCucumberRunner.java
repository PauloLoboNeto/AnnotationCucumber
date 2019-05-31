package annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import cucumber.api.junit.Cucumber;

public class ExtendedCucumberRunner extends Runner {
	@SuppressWarnings("rawtypes")
	private Class clazz;
	private Cucumber cucumber;

	public ExtendedCucumberRunner(@SuppressWarnings("rawtypes") Class clazzValue) throws Exception {
		clazz = clazzValue;
		cucumber = new Cucumber(clazzValue);
	}

	@Override
	public Description getDescription() {
		return cucumber.getDescription();
	}

	private void runPredefinedMethods(@SuppressWarnings("rawtypes") Class annotation) throws Exception {
		if (!annotation.isAnnotation()) {
			return;
		}
		Method[] methodList = this.clazz.getMethods();
		for (Method method : methodList) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation item : annotations) {
				if (item.annotationType().equals(annotation)) {
					method.invoke(annotation);
					break;
				}
			}
		}
	}

	@Override
	public void run(RunNotifier notifier) {
		try {
			runPredefinedMethods(RodarAntesDosTestes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cucumber.run(notifier);
		try {
			runPredefinedMethods(RodarDepoisDosTestes.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	