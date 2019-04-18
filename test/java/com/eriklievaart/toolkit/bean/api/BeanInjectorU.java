package com.eriklievaart.toolkit.bean.api;

import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.eriklievaart.toolkit.bean.api.annotate.Email;
import com.eriklievaart.toolkit.bean.api.annotate.NotBlank;
import com.eriklievaart.toolkit.bean.api.annotate.NotEmpty;
import com.eriklievaart.toolkit.bean.api.annotate.Regex;
import com.eriklievaart.toolkit.bean.api.annotate.Required;
import com.eriklievaart.toolkit.bean.api.annotate.Size;
import com.eriklievaart.toolkit.lang.api.AssertionException;
import com.eriklievaart.toolkit.lang.api.FormattedException;
import com.eriklievaart.toolkit.lang.api.check.Check;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.test.api.BombSquad;

public class BeanInjectorU {

	@Test
	public void injectStrings() {
		class Injectme {
			public String a;
			public String b;
		}
		Injectme injectme = new Injectme();

		Map<String, String> map = NewCollection.map();
		map.put("a", "alpha");
		map.put("b", "beta");

		new BeanInjector(map).inject(injectme);
		Check.isEqual(injectme.a, "alpha");
		Check.isEqual(injectme.b, "beta");
	}

	@Test
	public void validateRequiredPass() {
		class Injectme {
			@Required
			public String empty = "";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateRequiredFail() {
		class Injectme {
			@Required
			public String missing;
		}
		BombSquad.diffuse(BeanValidationException.class, "missing", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateNotEmptyPass() {
		class Injectme {
			@NotEmpty
			public String blank = " ";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateNotEmptyNull() {
		class Injectme {
			@NotEmpty
			public String missing;
		}
		BombSquad.diffuse(BeanValidationException.class, "missing", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateNotEmptyFail() {
		class Injectme {
			@NotEmpty
			public String empty = "";
		}
		BombSquad.diffuse(BeanValidationException.class, "empty", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateNotBlankPass() {
		class Injectme {
			@NotBlank
			public String data = "123";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateNotBlankNull() {
		class Injectme {
			@NotBlank
			public String missing = null;
		}
		BombSquad.diffuse(BeanValidationException.class, "missing", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateNotBlankEmpty() {
		class Injectme {
			@NotBlank
			public String empty = "";
		}
		BombSquad.diffuse(BeanValidationException.class, "empty", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateNotBlankFail() {
		class Injectme {
			@NotBlank
			public String blank = " \t ";
		}
		BombSquad.diffuse(BeanValidationException.class, "blank", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateSizeStringPass() {
		class Injectme {
			@Size(min = 1, max = 5)
			public String valid = "123";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateSizeStringMin() {
		class Injectme {
			@Size(min = 1, max = 5)
			public String empty = "";
		}
		BombSquad.diffuse(BeanValidationException.class, "empty", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateSizeStringMax() {
		class Injectme {
			@Size(min = 1, max = 5)
			public String max = "1234567890";
		}
		BombSquad.diffuse(BeanValidationException.class, "max", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateSizeIntegerPass() {
		class Injectme {
			@Size(min = 3, max = 5)
			public int pass = 4;
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateSizeIntegerMin() {
		class Injectme {
			@Size(min = 3, max = 5)
			public int min = 1;
		}
		BombSquad.diffuse(BeanValidationException.class, "min", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateSizeIntegerMax() {
		class Injectme {
			@Size(min = 3, max = 5)
			public int max = 8;
		}
		BombSquad.diffuse(BeanValidationException.class, "max", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateSizeIgnoreNull() {
		class Injectme {
			@Size(min = 1, max = 5)
			public String missing;
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateRegexPass() {
		class Injectme {
			@Regex("\\d++")
			public String number = "12345678";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateRegexFail() {
		class Injectme {
			@Regex("\\D++")
			public String number = "12345678";
		}
		BombSquad.diffuse(BeanValidationException.class, () -> new BeanInjector().validate(new Injectme()), e -> {
			Assertions.assertThat(e.getErrors()).containsValue("number must match \\D++");
		});
	}

	@Test
	public void validateRegexCustomMessage() {
		class Injectme {
			@Regex(value = "\\D++", message = "toedelieflips")
			public String number = "12345678";
		}
		BombSquad.diffuse(BeanValidationException.class, () -> new BeanInjector().validate(new Injectme()), e -> {
			Assertions.assertThat(e.getErrors()).containsValue("toedelieflips");
		});
	}

	@Test
	public void validateEmailPassShort() {
		class Injectme {
			@Email
			public String email = "e@e.e";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateEmailPassLong() {
		class Injectme {
			@Email
			public String email = "sdfg@dd.da";
		}
		new BeanInjector().validate(new Injectme());
	}

	@Test
	public void validateEmailFail() {
		class Injectme {
			@Email
			public String word = "invalid";
		}
		BombSquad.diffuse(BeanValidationException.class, "word", () -> new BeanInjector().validate(new Injectme()));
	}

	@Test
	public void validateCorrectType() {
		class Injectme {
			@Email
			public int err = 10;
		}
		BombSquad.diffuse(AssertionException.class, "err: @Email cannot be assigned to fields of type int", () -> {
			new BeanInjector().validate(new Injectme());
		});
	}

	@Test
	public void validateMultiFieldFail() {
		class Injectme {
			@Required
			public String present = "mule";
			@Email
			public String word = "invalid";
			@Regex("\\d++")
			public String number = "12345678";
			@Required
			public String missing = null;
		}
		try {
			new BeanInjector().validate(new Injectme());
			throw new FormattedException("Should have thrown BeanValidationException");

		} catch (BeanValidationException bve) {
			Assertions.assertThat(bve.getErrors().keySet()).containsExactlyInAnyOrder("word", "missing");
		}
	}
}
